package zhy.blog.dao.leveldb;

import zhy.blog.dao.IUcrdDao;
import zhy.blog.dao.Initializable;
import zhy.blog.entity.BaseEntity;
import zhy.blog.util.Status;
import zhy.util.leveldb.client.LevelDbHelper;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

abstract class BaseDao<T extends BaseEntity> implements IUcrdDao<T>, Initializable {
    LevelDbHelper helper;

    public BaseDao() {
        helper = new LevelDbHelper(dbName());
    }

    /**
     * Name of database
     *
     * @return name
     */
    abstract @NotNull String dbName();

    @Override
    public int insertNormal(T toInsert) {
        Date now = new Date();
        toInsert.normal()
                .setCreateDate(now)
                .setUpdateDate(now);
        return insert(toInsert);
    }

    @Override
    public int insertInitialized(T toInsert) {
        Date now = new Date();
        toInsert.initialized()
                .setCreateDate(now)
                .setUpdateDate(now);
        return insert(toInsert);
    }

    @Override
    public int insert(T toInsert) {
        int id = helper.idNextAndIncrement();
        toInsert.setId(id);
        helper.putOrAdd(String.valueOf(id), toInsert.toJSON());
        return id;
    }

    @Override
    public int update(T toUpdate) {
        helper.putOrAdd(String.valueOf(toUpdate.getId()), toUpdate.toJSON());
        return toUpdate.getId();
    }

    @Override
    public int delete(int id) {
        helper.delete(String.valueOf(id));
        return 1;
    }

    @Override
    public int deleteBy(T condition) {
        List<Condition> conditions = ConditionFilter.generateCondition(condition);
        return helper.deleteBy(conditions);
    }

    @Override
    public int count(T article) {
        List<Condition> conditions = ConditionFilter.generateCondition(article);
        conditions.add(new Condition("status", String.valueOf(Status.INVALID), Condition.NOT_EQUAL));
        conditions.add(new Condition("status", String.valueOf(Status.OLD), Condition.NOT_EQUAL));
        return helper.find(conditions, -1, -1).size();
    }

    @Override
    final public void init() {
        helper.deleteAndInit();
    }
}

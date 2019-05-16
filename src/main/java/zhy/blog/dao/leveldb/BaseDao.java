package zhy.blog.dao.leveldb;

import com.alibaba.fastjson.JSON;
import zhy.blog.dao.IUcrdDao;
import zhy.blog.dao.Initializable;
import zhy.blog.entity.BaseEntity;
import zhy.blog.util.Page;
import zhy.blog.util.Status;
import zhy.util.leveldb.client.LevelDbHelper;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;
import zhy.util.leveldb.query.Operation;

import javax.validation.constraints.NotNull;
import java.lang.reflect.ParameterizedType;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

abstract class BaseDao<T extends BaseEntity> implements IUcrdDao<T>, Initializable {
    private Class<T> tClass;
    LevelDbHelper helper;

    BaseDao() {
        helper = new LevelDbHelper(dbName());
        tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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
        conditions.add(new Condition("status", String.valueOf(Status.INVALID), Operation.NOT_EQUAL));
        conditions.add(new Condition("status", String.valueOf(Status.OLD), Operation.NOT_CONTAIN));
        return helper.find(conditions, -1, -1).size();
    }

    @Override
    public T get(int id) {
        return JSON.parseObject(helper.get(String.valueOf(id)), tClass);
    }

    @Override
    public List<T> find(T t) {
        return find(t, null);
    }

    @Override
    public List<T> find(T t, Page page) {
        int start = page == null ? -1 : page.getStartNum();
        int end = page == null ? -1 : page.getEndNum();
        List<Condition> conditions = ConditionFilter.generateCondition(t);
        conditions.add(new Condition("status", String.valueOf(Status.INVALID), Operation.NOT_EQUAL));
        conditions.add(new Condition("status", String.valueOf(Status.OLD), Operation.NOT_EQUAL));
        Map<String, String> map = helper.find(conditions, start, end);
        return map.values()
                .parallelStream()
                .map(a -> JSON.parseObject(a, tClass))
                .sorted(Comparator.comparingInt(BaseEntity::getId).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public final void init() {
        helper.deleteAndInit();
    }
}

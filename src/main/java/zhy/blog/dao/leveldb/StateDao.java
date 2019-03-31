package zhy.blog.dao.leveldb;

import zhy.blog.dao.IStateDao;
import zhy.blog.entity.State;

import javax.validation.constraints.NotNull;

public class StateDao extends BaseDao<State> implements IStateDao {
    @Override
    @NotNull String dbName() {
        return "state";
    }

    @Override
    public String get(String key) {
        return helper.get(key);
    }

    @Override
    public void put(String key, String value) {
        helper.putOrAdd(key, value);
    }
}

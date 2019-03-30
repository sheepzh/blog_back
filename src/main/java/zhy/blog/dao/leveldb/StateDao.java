package zhy.blog.dao.leveldb;

import zhy.blog.dao.IStateDao;
import zhy.blog.entity.State;
import zhy.util.leveldb.client.LevelDbHelper;

public class StateDao extends BaseDao<State> implements IStateDao {
    public StateDao() {
        helper = new LevelDbHelper("state");
    }

    @Override
    public String get(String key) {
        return helper.get(key);
    }

    @Override
    public void put(String key, String value) {
         helper.putOrAdd(key,value);
    }
}

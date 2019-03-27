package zhy.blog.dao;

import zhy.blog.entity.State;

public interface IStateDao extends IUcrdDao<State> {
    String get(String key);

    void put(String key, String value);
}

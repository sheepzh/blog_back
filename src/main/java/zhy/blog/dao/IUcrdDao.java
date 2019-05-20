package zhy.blog.dao;

import zhy.blog.util.Page;

import java.util.List;

public interface IUcrdDao<T> {
    T get(int id);

    List<T> find(T condition);

    List<T> find(T condition, Page page);

    int insert(T toInsert);

    int update(T toUpdate);

    int delete(int id);

    int deleteBy(T condition);

    int count(T condition);

    int insertNormal(T toInsert);

    int insertInitialized(T toInsert);
}

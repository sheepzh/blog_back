package zhy.blog.dao;

public interface IUcrdDao<T> {
    int insert(T toInsert);

    int update(T toUpdate);

    int delete(int id);

    int deleteBy(T condition);

    int count(T article);

    int insertNormal(T toInsert);

    int insertInitialized(T toInsert);
}

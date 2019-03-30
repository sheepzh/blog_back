package zhy.blog.dao;

import zhy.blog.entity.Comment;

import java.util.List;

public interface ICommentDao extends IUcrdDao<Comment> {
    Comment get(int id);

    List<Comment> find(Comment comment);

    /**
     * Find the comment including of themselves
     * @param comment condition
     * @return list of comment
     */
    List<Comment> findAll(Comment comment);
}

package zhy.blog.dao;

import zhy.blog.entity.Comment;

import java.util.List;

public interface ICommentDao extends IUcrdDao<Comment> {

    /**
     * Find the comment including of themselves
     *
     * @param comment condition
     * @return list of comment
     */
    List<Comment> findAll(Comment comment);
}

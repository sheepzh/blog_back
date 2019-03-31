package zhy.blog.dao.leveldb;

import zhy.blog.dao.ICommentDao;
import zhy.blog.entity.Comment;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

public class CommentDao extends BaseDao<Comment> implements ICommentDao {

    @NotNull
    @Override
    String dbName() {
        return "comment";
    }

    @Override
    public List<Comment> findAll(Comment comment) {
        List<Comment> find = find(comment);
        List<Comment> result = new LinkedList<>();
        List<Comment> temp = new LinkedList<>();
        do {
            result.addAll(find);
            temp.clear();
            find.stream()
                    .map(c -> new Comment().setTargetId(c.getId()).setTargetType(Comment.COMMENT))
                    .map(this::find)
                    .forEach(temp::addAll);
            find.clear();
            find.addAll(temp);
        } while (!temp.isEmpty());
        return result;
    }
}

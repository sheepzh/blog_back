package zhy.blog.dao.leveldb;

import com.alibaba.fastjson.JSON;
import zhy.blog.dao.ICommentDao;
import zhy.blog.entity.Comment;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommentDao extends BaseDao<Comment> implements ICommentDao {

    @NotNull
    @Override
    String dbName() {
        return "comment";
    }

    @Override
    public Comment get(int id) {
        return JSON.parseObject(helper.get(String.valueOf(id)), Comment.class);
    }

    @Override
    public List<Comment> find(Comment comment) {
        List<Condition> conditions = ConditionFilter.generateCondition(comment);
        Map<String, String> map = helper.find(conditions, -1, -1);
        return map.values()
                .parallelStream()
                .map(a -> JSON.parseObject(a, Comment.class))
                .collect(Collectors.toList());
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

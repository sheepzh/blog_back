package zhy.blog.dao.leveldb;

import com.alibaba.fastjson.JSON;
import zhy.blog.dao.IGroupDao;
import zhy.blog.entity.GroupNode;
import zhy.util.leveldb.query.Condition;
import zhy.util.leveldb.query.ConditionFilter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupDao extends BaseDao<GroupNode> implements IGroupDao {

    @NotNull
    @Override
    String dbName() {
        return "group_node";
    }

    @Override
    public GroupNode get(int id) {
        return JSON.parseObject(helper.get(String.valueOf(id)), GroupNode.class);
    }

    @Override
    public List<GroupNode> find(GroupNode node) {
        List<Condition> conditions = ConditionFilter.generateCondition(node);
        Map<String, String> map = helper.find(conditions, -1, -1);
        return map.values()
                .parallelStream()
                .map(a -> JSON.parseObject(a, GroupNode.class))
                .collect(Collectors.toList());
    }
}

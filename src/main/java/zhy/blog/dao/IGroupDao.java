package zhy.blog.dao;

import zhy.blog.entity.GroupNode;

import java.util.List;

public interface IGroupDao extends IUcrdDao<GroupNode> {
    GroupNode get(int id);

    List<GroupNode> find(GroupNode node);
}

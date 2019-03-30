package zhy.blog.entity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GroupTreeNode {
    private GroupTreeNode parent;

    private GroupNode node;

    private List<GroupTreeNode> children = new LinkedList<>();

    public GroupTreeNode(GroupNode node) {
        this.node = node;
    }

    public int getId() {
        return node.getId();
    }

    public List<GroupTreeNode> getChildren() {
        return children;
    }

    public GroupTreeNode addChildren(GroupTreeNode children) {
        this.children.add(children);
        return this;
    }

    public GroupTreeNode getParent() {
        return parent;
    }

    public GroupTreeNode setParent(GroupTreeNode parent) {
        this.parent = parent;
        return this;
    }

    public GroupNode getNode() {
        return node;
    }

    public GroupTreeNode setNode(GroupNode node) {
        this.node = node;
        return this;
    }

    public static Map<Integer, GroupTreeNode> treeify(List<GroupNode> groupNodes) {
        if (groupNodes.isEmpty()) return null;
        Map<Integer, GroupTreeNode> map = groupNodes.stream()
                .map(GroupTreeNode::new)
                .collect(Collectors.toMap(GroupTreeNode::getId, Function.identity()));
        for (Map.Entry<Integer, GroupTreeNode> entry : map.entrySet()) {
            Integer i = entry.getKey();
            GroupTreeNode n = entry.getValue();
            Integer parentId = n.node.getParent();
            if (parentId == null) {
                continue;
            }
            GroupTreeNode parentNode = map.get(parentId);
            if (parentNode != null) {
                parentNode.addChildren(n);
                n.setParent(parentNode);
            }
        }
        return map;
    }

    public static List<GroupNode> allChildren(List<GroupNode> groupNodes, int targetId) {
        Map<Integer, GroupTreeNode> map = treeify(groupNodes);
        GroupTreeNode target = map.get(targetId);
        if (target == null) return Collections.emptyList();
        List<GroupTreeNode> temp = target.children;
        List<GroupNode> result = new LinkedList<>();
        result.add(target.node);
        while (!temp.isEmpty()) {
            for (GroupTreeNode t : temp) {
                result.add(t.node);
                temp.addAll(t.children);
                temp.remove(t);
            }
        }
        return result;
    }
}

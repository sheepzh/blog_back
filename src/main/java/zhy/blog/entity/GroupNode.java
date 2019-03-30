package zhy.blog.entity;

import zhy.blog.util.ObjectUtil;

/**
 * @author zhyyy
 * @since 20190307
 */
public class GroupNode extends BaseEntity {
    private Integer parent;
    private String[] tags;
    private Integer level;
    private String name;

    public Integer getParent() {
        return parent;
    }

    public GroupNode setParent(Integer parent) {
        this.parent = parent;
        return this;
    }

    public String[] getTags() {
        return tags;
    }

    public GroupNode setTags(String[] tags) {
        this.tags = tags;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public GroupNode setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupNode setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean isValid() {
        return ObjectUtil.nonNull(name, level);
    }
}

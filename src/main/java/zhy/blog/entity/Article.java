package zhy.blog.entity;

import zhy.blog.util.BlogException;
import zhy.blog.util.StringUtil;

public class Article extends BaseEntity {

    /**
     * Title of this article
     */
    private String title;
    /**
     * Comment of this article
     */
    private String content;

    private Integer groupId;

    public String getContent() {
        return content;
    }

    public Article setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getGroupId() {
        return groupId;
    }

    public Article setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    @Override
    public void assertValid() {
        if (StringUtil.existsBlank(title, content))
            throw new BlogException("Invalid article");
    }
}
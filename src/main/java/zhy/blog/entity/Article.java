package zhy.blog.entity;

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
}
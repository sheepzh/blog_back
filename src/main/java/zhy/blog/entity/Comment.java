package zhy.blog.entity;

import java.util.Date;

/**
 * Comment of all the work
 */
public class Comment extends BaseEntity {
    /**********Target type begin************/
    public static final int ARTICLE = 0;
    public static final int COMMENT = 1;
    public static final int PHOTO = 2;
    public static final int POEM = 3;
    public static final int WORK_COLLECTION_OR_BOOK = 4;
    public static final int CODE_WORK = 5;
    public static final int OTHER = Integer.MAX_VALUE;
    /**********Target type end************/

    private Date createDate;
    private String content;
    private Integer targetId;
    private String user;
    private String email;
    private Integer targetType;
    private String targetUrl;


    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public Comment setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public Comment setTargetId(int targetId) {
        this.targetId = targetId;
        return this;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public Comment setTargetType(int targetType) {
        this.targetType = targetType;
        return this;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public Comment setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Comment setUser(String user) {
        this.user = user;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Comment setEmail(String email) {
        this.email = email;
        return this;
    }
}

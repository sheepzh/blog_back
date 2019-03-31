package zhy.blog.entity;

import zhy.blog.util.BlogException;
import zhy.blog.util.StringUtil;

/**
 * Friend link of my blog
 *
 * @author zhy
 */
public class FriendLink extends BaseEntity {
    private String url;
    private String friendName;
    private String webName;
    private String friendInfo;
    private String friendTag;


    public String getUrl() {
        return url;
    }

    public FriendLink setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getFriendName() {
        return friendName;
    }

    public FriendLink setFriendName(String friendName) {
        this.friendName = friendName;
        return this;
    }

    public String getWebName() {
        return webName;
    }

    public FriendLink setWebName(String webName) {
        this.webName = webName;
        return this;
    }

    public String getFriendInfo() {
        return friendInfo;
    }

    public FriendLink setFriendInfo(String friendInfo) {
        this.friendInfo = friendInfo;
        return this;
    }

    public String getFriendTag() {
        return friendTag;
    }

    public FriendLink setFriendTag(String friendTag) {
        this.friendTag = friendTag;
        return this;
    }

    @Override
    public void assertValid() {
        if (StringUtil.existsBlank(friendName, url))
            throw new BlogException("Invalid FriendLink");
    }
}

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
    private String name;
    private String webName;
    private String info;
    private String tag;
    private String location;


    public String getUrl() {
        return url;
    }

    public FriendLink setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getName() {
        return name;
    }

    public FriendLink setName(String name) {
        this.name = name;
        return this;
    }

    public String getWebName() {
        return webName;
    }

    public FriendLink setWebName(String webName) {
        this.webName = webName;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public FriendLink setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public FriendLink setTag(String tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public BaseEntity assertValid() {
        if (StringUtil.existsBlank(name, url))
            throw new BlogException("Invalid FriendLink");
        return this;
    }
}

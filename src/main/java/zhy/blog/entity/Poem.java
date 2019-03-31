package zhy.blog.entity;

import zhy.blog.util.BlogException;
import zhy.blog.util.StringUtil;

import java.util.Arrays;
import java.util.List;

public class Poem extends BaseEntity {
    private volatile List<String> contentLines;
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public Poem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Poem setContent(String content) {
        this.content = content;
        if (StringUtil.isBlank(content)) {
            contentLines = null;
        } else {
            contentLines = Arrays.asList(content.split("\n"));
        }
        return this;
    }

    public List<String> getContentLines() {
        return contentLines;
    }

    public Poem setContentLines(List<String> contentLines) {
        this.contentLines = contentLines;
        return this;
    }

    @Override
    public void assertValid() {
        if (StringUtil.existsBlank(title, content))
            throw new BlogException("Invalid poem");
    }
}

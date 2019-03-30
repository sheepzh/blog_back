package zhy.blog.util;

public class LevelDbConfig {
    private String dbRoot;
    private String charset;

    public String getDbRoot() {
        return dbRoot;
    }

    public LevelDbConfig setDbRoot(String dbRoot) {
        this.dbRoot = dbRoot;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public LevelDbConfig setCharset(String charset) {
        this.charset = charset;
        return this;
    }
}

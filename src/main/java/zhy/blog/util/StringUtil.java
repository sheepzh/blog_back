package zhy.blog.util;

public class StringUtil {
    public static final String EMPTY = "";

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}

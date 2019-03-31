package zhy.blog.util;

/**
 * String tools
 *
 * @author zhy
 */
public class StringUtil {
    public static final String EMPTY = "";

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static boolean nonBlank(String... s) {
        for (String s1 : s) {
            if (isBlank(s1)) return false;
        }
        return true;
    }

    public static String sameChars(char c, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(c);
        return sb.toString();
    }
}

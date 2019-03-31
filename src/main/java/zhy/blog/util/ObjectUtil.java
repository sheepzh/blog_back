package zhy.blog.util;

/**
 * Object tools
 *
 * @author zhy
 */
public class ObjectUtil {
    public static boolean nonNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }
}
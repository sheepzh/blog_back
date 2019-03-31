package zhy.blog.util;

/**
 * Object tools
 *
 * @author zhy
 */
public class ObjectUtil {
    public static boolean existsNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return true;
        }
        return false;
    }
}
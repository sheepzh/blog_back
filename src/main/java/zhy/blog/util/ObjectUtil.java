package zhy.blog.util;

public class ObjectUtil {
    public static boolean nonNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) return false;
        }
        return true;
    }
}
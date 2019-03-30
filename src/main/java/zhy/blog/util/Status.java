package zhy.blog.util;

import java.util.Arrays;

/**
 * The status of all the entities,article,content etc.
 *
 * @author zhyyy
 * @see zhy.blog.entity.Article
 * @since 20190307
 */
public interface Status {

    int NORMAL = 0B1;
    int INVALID = 0B10;
    /**
     * Out of date
     */
    int OLD = 0B100;
    /**
     * Forbid to content in articles or other work
     */
    int FORBIDDEN_CONTENT = 0B1000;

    /**
     * Whether updated after created
     */
    int INITIALIZED = 0B10000;

    /**
     * Judge the source condition satisfies the target condition.
     * The source condition should not be narrower than the target condition
     *
     * @param target target condition
     * @param source source condition
     * @return <code>true</code>,if satisfies,<code>false</code> else.
     */
    default boolean satisfies(int target, int source) {
        return (target & source) == target;
    }

    /**
     * Judge whether the source condition is the same as the target condition.
     *
     * @param target target condition
     * @param source source condition
     * @return <code>true</code>,if equals,<code>false</code> else.
     */
    default boolean equals(int target, int source) {
        return target == source;
    }

    /**
     * Contact all the status.
     *
     * @param status to contact
     * @return simple status
     */
    default int generate(int... status) {
        return Arrays.stream(status).reduce((a, b) -> a & b).orElse(0);
    }
}

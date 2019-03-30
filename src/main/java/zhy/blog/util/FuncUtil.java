package zhy.blog.util;

import zhy.blog.entity.BaseEntity;

import java.util.Collection;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class FuncUtil {
    public static final Function<? super BaseEntity, Integer> MAP_2_ID = BaseEntity::getId;
    public static final BinaryOperator<Boolean> OR = Boolean::logicalOr;
    public static final BinaryOperator<Boolean> AND = Boolean::logicalAnd;
    public static final BinaryOperator<? super Object> RIGHT = (a, b) -> b;
    public static final BinaryOperator<? super Object> LEFT = (a, b) -> a;

    public static BinaryOperator<String> joinStr(String contactStr) {
        return (a, b) -> a + contactStr + b;
    }
}

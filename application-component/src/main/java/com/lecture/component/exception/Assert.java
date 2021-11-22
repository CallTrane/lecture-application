package com.lecture.component.exception;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @className: Assert
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 14:30
 */
public final class Assert {

    private static final Assert FALSE = new Assert(false);
    private final Boolean predicate;

    private Assert(boolean predicate) {
        this.predicate = predicate;
    }

    public static Assert isTrue(boolean predicate) {
        return predicate ? new Assert(predicate) : FALSE;
    }

    public void ifPresentOrElse(Runnable beTrue, Runnable toElse) {
        if (predicate) {
            beTrue.run();
        } else {
            toElse.run();
        }
    }

    /**
     * 如果不是true，则抛出异常
     *
     * @param expression
     * @param errorMessage
     */
    public static void isTrueOrElseThrow(boolean expression, String errorMessage) {
        if (!expression) {
            throw new BizException(errorMessage);
        }
    }
}

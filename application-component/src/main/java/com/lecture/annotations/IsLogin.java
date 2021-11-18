package com.lecture.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: IsLogin
 * @description: 判断是否需要登录
 * @author: carl
 * @date: 2021/11/15 23:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLogin {
    boolean value() default true;
}

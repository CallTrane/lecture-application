package com.lecture.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @className: BaseException
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 14:17
 */
public abstract class BaseException extends RuntimeException {

    @Setter
    @Getter
    private String code;

    public BaseException(String errorMessage) {
        super(errorMessage);
    }

    public BaseException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
    }

    public BaseException(String errorMessage, Throwable e) {
        super(errorMessage, e);
    }

    public BaseException(String code, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.code = code;
    }
}

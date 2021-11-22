package com.lecture.component.exception;

/**
 * @className: SystemException
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/20 18:01
 */
public class SystemException extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "SYS_ERROR";

    public SystemException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public SystemException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public SystemException(String errorMessage, Throwable e) {
        super(DEFAULT_ERROR_CODE, errorMessage, e);
    }

    public SystemException(String code, String errorMessage, Throwable e) {
        super(code, errorMessage, e);
    }
}

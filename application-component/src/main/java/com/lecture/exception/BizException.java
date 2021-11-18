package com.lecture.exception;

/**
 * @className: BizException
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 14:26
 */
public class BizException extends BaseException{

    private static final String DEFAULT_ERROR_CODE = "BIZ_ERROR";

    public BizException(String errorMessage, Throwable e) {
        super(DEFAULT_ERROR_CODE, errorMessage, e);
    }

    public BizException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }
}

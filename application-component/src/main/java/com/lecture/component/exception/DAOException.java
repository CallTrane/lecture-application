package com.lecture.component.exception;

/**
 * @className: DAOException
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 14:26
 */
public class DAOException extends BaseException {

    private static final String DEFAULT_ERROR_CODE = "DAO_ERROR";

    public DAOException(String errorMessage) {
        super(DEFAULT_ERROR_CODE, errorMessage);
    }

    public DAOException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public DAOException(String errorMessage, Throwable e) {
        super(DEFAULT_ERROR_CODE, errorMessage, e);
    }

    public DAOException(String code, String errorMessage, Throwable e) {
        super(code, errorMessage, e);
    }
}

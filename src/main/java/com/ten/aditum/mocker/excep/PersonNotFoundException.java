package com.ten.aditum.mocker.excep;

/**
 * 数据未定义异常
 */
public class PersonNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PersonNotFoundException() {
        super("Person not found in access strategy by bean defined.");
    }

    public PersonNotFoundException(String message, Throwable cause,
                                   boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(Throwable cause) {
        super(cause);
    }
}

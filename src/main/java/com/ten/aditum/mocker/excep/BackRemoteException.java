package com.ten.aditum.mocker.excep;

/**
 * 后端数据库服务未响应异常
 */
public class BackRemoteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BackRemoteException() {
        super("Back server not found or reject.");
    }

    public BackRemoteException(String message,
                               Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BackRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackRemoteException(String message) {
        super(message);
    }

    public BackRemoteException(Throwable cause) {
        super(cause);
    }
}

package com.geekerstar.easyexcel.util;

/**
 * @author geekerstar
 * date: 2019/9/22 13:37
 * description: 捕获相关异常
 */
public class ExcelException extends RuntimeException {
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ExcelException(String message) {
        super(message);
    }
}

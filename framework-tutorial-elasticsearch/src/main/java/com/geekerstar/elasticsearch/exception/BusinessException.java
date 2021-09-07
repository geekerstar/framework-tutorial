package com.geekerstar.elasticsearch.exception;

import lombok.Getter;

/**
 * @author geekerstar
 * @date 2021/9/1 17:40
 * @description
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 6237152976831943115L;

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;

    public BusinessException(String code, String message) {
        super("[code=" + code + ", msg=" + message + "]");
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String msgFormat, Object... args) {
        super("[code=" + code + ", msg=" + String.format(msgFormat, args) + "]");
        this.code = code;
        this.message = String.format(msgFormat, args);
    }

    public BusinessException(BusinessException e, Object... args) {
        super("[code=" + e.getCode() + ", msg=" + String.format(e.getMessage(), args) + "]");
        this.code = e.getCode();
        this.message = String.format(e.getMessage(), args);
    }
}



package com.geekerstar.easyexcel.model;

import lombok.Data;

/**
 * 返回实体
 */
@Data
public class WebResponse extends BaseObject {
    private String code;
    private Boolean isSuccess;
    private String msg;

    public WebResponse(ResponseEnum responseEnum, String msg) {
        this.code = responseEnum.getCode();
        this.isSuccess = responseEnum.getIsSuccess();
        this.msg = msg;
    }
}

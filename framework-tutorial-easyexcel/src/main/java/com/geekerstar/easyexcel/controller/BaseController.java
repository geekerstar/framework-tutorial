package com.geekerstar.easyexcel.controller;


import com.geekerstar.easyexcel.model.ResponseEnum;
import com.geekerstar.easyexcel.model.WebResponse;

/**
 * 基础控制器
 */
public class BaseController {

    public static WebResponse success(String msg){
        return new WebResponse(ResponseEnum.SUCCESS,msg);
    }

    public static WebResponse fail(String msg){
        return new WebResponse(ResponseEnum.FAIL,msg);
    }
}

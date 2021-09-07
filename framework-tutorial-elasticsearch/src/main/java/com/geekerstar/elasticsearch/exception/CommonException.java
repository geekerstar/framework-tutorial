package com.geekerstar.elasticsearch.exception;

/**
 * @author geekerstar
 * @date 2021/9/1 17:39
 * @description 错误码约定:错误码为字符串类型，共5位，分成两个部分：错误产生来源+四位数字编号。错误产生来源分为A/B/C，A表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；B表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；C表示错误来源于第三方服务，比如CDN服务出错，消息投递超时等问题；四位数字编号从0001到9999，大类之间的步长间距预留100
 */
public class CommonException {

    /**
     * ************************************** 用户异常 **************************************
     */
    public static final BusinessException ERROR_REQUEST = new BusinessException("A0001", "无效的请求");
    public static final BusinessException PARAM_ERROR = new BusinessException("A0005", "请求参数有误");

    /**
     * ************************************** 系统异常 **************************************
     */
    public static final BusinessException SYS_ERROR = new BusinessException("B0001", "搜索服务异常,请查看异常日志");
    public static final BusinessException IO_ERROR = new BusinessException("B0005", "系统内部IO异常");
    public static final BusinessException ADDRESS_ALREADY_IN_USE = new BusinessException("B0010", "端口被占用");
    public static final BusinessException UNKNOWN_PROCESS_TYPE = new BusinessException("B0015", "未知的处理类型");

    /**
     * ************************************** 三方异常 **************************************
     */
    public static final BusinessException THREE_PART_DEMO = new BusinessException("C0001", "三方异常示例");
}

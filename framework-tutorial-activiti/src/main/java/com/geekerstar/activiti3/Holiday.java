package com.geekerstar.activiti3;

import java.io.Serializable;
import java.util.Date;

/**
 * 请假实体类:
 *    注意POJO类型，一定要实现Serializable接口，否则在存储这个pojo时就会报异常
 */
public class Holiday implements Serializable {
    private Integer id;
    private String holidayName;//申请人的名字
    private Date beginDate;//开始时间
    private Date endDate;//结束日期
    private Float num;//请假天数
    private String reason;//事由
    private String type;//请假类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.geekerstar.dao;

import org.springframework.stereotype.Repository;

/**
 * @author geekerstar
 * @date 2018/12/8
 * description
 */

/**
 * 名字是默认类名首字母小写
 */
@Repository
public class BookDao {
    private String lable="1";

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "lable='" + lable + '\'' +
                '}';
    }
}

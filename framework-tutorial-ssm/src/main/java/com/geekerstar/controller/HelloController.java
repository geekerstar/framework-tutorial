package com.geekerstar.controller;

import com.geekerstar.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class HelloController {

    @Resource(name = "user")
    private Student student;

    @RequestMapping(value = "/hello")
    public String hello(){

        student.setStudentname("张三");
        student.setStudentid(101);
        student.setStudentclass("不知道");

        System.out.println(student);

        return "hello";
    }


}

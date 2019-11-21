package com.geekerstar.controller;

import com.geekerstar.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author geekerstar
 * @date 2018/12/8
 * description
 */
@Controller
public class BookController {

    @Autowired
    private BookService bookService;
}

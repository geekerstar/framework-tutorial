package com.geekerstar.service;

import com.geekerstar.dao.BookDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author geekerstar
 * @date 2018/12/8
 * description
 */
@Service
public class BookService {

//    @Qualifier("bookDao")
//    @Autowired(required = false)
//    @Resource(name = "bookDao")
    @Inject
    private BookDao bookDao2;

    public void print(){
        System.out.println(bookDao2);

    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookDao=" + bookDao2 +
                '}';
    }
}

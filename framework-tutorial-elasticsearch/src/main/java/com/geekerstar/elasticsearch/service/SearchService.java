package com.geekerstar.elasticsearch.service;

import com.geekerstar.elasticsearch.domain.entity.User;

import java.util.List;

/**
 * @author geekerstar
 * @date 2021/9/21 09:25
 * @description
 */
public interface SearchService {
    String createUserDocument(User user) throws Exception;

    User findById(String id) throws Exception;

    String updateUser(User user) throws Exception;

    List<User> findAll() throws Exception;

    List<User> findUserByName(String name) throws Exception;

    String deleteUserDocument(String id) throws Exception;

    List<User> searchByKeyword(String keyword) throws Exception;
}

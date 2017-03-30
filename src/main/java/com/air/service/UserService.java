package com.air.service;

import com.air.po.User;

import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:05
 */
public interface UserService {

    int saveUser(User user);

    void updateUser(User user);

    List<User> listAllUser(User user);

    void deleteUserById(Long id);

    User getUserById(Long id);

    boolean checkIdCardIsExist(String idCardNumber);
}

package com.air.service.impl;

import com.air.dao.IDataBaseManager;
import com.air.po.User;
import com.air.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by linux on 2017年03月28日.
 * Time 01:06
 */
@Service
public class UserServiceImpl implements UserService {
    private final String USER_GET_BASE_SQL = "select t from User  t where 1=1 ";
    @Resource
    IDataBaseManager<User> dataBaseManager;

    public Long saveUser(User user) {
        return (Long) dataBaseManager.create(user);
    }

    @Transactional
    public void updateUser(User user) {
        dataBaseManager.update(user);
    }

    public List<User> listAllUser(User user) {
        StringBuilder stringBuilder = new StringBuilder(USER_GET_BASE_SQL);
        List params = new LinkedList();
        int i = 0;
        if (user != null) {
            if (StringUtils.isNotEmpty(user.getLoginName())) {
                stringBuilder.append(" and t.loginName like ?").append(i);
                params.add(user.getLoginName() + "%");
                ++i;
            }
            if (StringUtils.isNotEmpty(user.getName())) {
                stringBuilder.append(" and t.name=?").append(i);
                params.add(user.getName());
                ++i;
            }
            if (StringUtils.isNotEmpty(user.getIdCardNumber())) {
                stringBuilder.append(" and t.idCardNumber=?").append(i);
                params.add(user.getIdCardNumber());
                ++i;
            }

            if (StringUtils.isNotEmpty(user.getPassword())) {
                stringBuilder.append(" and t.password=?").append(i);
                params.add(user.getPassword());
                ++i;
            }

            if (StringUtils.isNotEmpty(user.getPhone())) {
                stringBuilder.append(" and t.phone=?").append(i);
                params.add(user.getPhone());
                ++i;
            }

            if (user.getSex() <= 1) {
                stringBuilder.append(" and t.sex=?").append(i);
                params.add(user.getSex());
                ++i;
            }

        }
        return dataBaseManager.query(stringBuilder.toString(), params);
    }

    @Transactional
    public void deleteUserById(Long id) {
        dataBaseManager.delete(User.class, id);
    }

    public User getUserById(Long id) {
        return dataBaseManager.get(User.class, id);
    }

    public boolean checkIdCardIsExist(String idCardNumber) {
        boolean flag = false;
        List<User> users = dataBaseManager.query(new StringBuilder(USER_GET_BASE_SQL).append(" and t.idCardNumber=?0").toString(), Arrays.asList(idCardNumber));
        if (users != null && users.size() > 0) {
            flag = true;
        }
        return flag;
    }
}

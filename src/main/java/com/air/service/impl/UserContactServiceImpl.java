package com.air.service.impl;

import com.air.dao.DataBaseManagerImpl;
import com.air.po.UserContact;
import com.air.service.UserContactService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linux on 2017年03月30日.
 * Time 16:43
 */
@Service
public class UserContactServiceImpl implements UserContactService {
    private static final String USERCONTACT_LIST_BY_USERID = "select t from UserContact t where t.userId=?0";
    @Resource
    private DataBaseManagerImpl<UserContact> dataBaseManager;

    /**
     * 根据用户id获取用户联系人列表
     *
     * @param id
     * @return
     */
    public List<UserContact> listUserContacts(Long id) {
        return dataBaseManager.query(USERCONTACT_LIST_BY_USERID, Arrays.asList(id));
    }

    /**
     * 添加一条联系人记录
     *
     * @param userContact
     */
    @Transactional
    public void saveContact(UserContact userContact) {
        dataBaseManager.create(userContact);
    }
}

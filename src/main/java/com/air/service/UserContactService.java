package com.air.service;

import com.air.po.UserContact;

import java.util.List;

/**
 * Created by linux on 2017年03月30日.
 * Time 16:43
 */
public interface UserContactService {
    /**
     * 根据用户id获取用户的联系人列表
     *
     * @param id
     * @return
     */
    List<UserContact> listUserContacts(Long id);

    /**
     * 添加一条联系人记录
     *
     * @param userContact
     */
    void saveContact(UserContact userContact);
}

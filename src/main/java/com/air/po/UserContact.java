package com.air.po;

import javax.persistence.*;

/**
 * Created by linux on 2017年03月30日.
 * Time 02:45
 */
@Entity
public class UserContact {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;//用户id

    private Long contactUserId;//用户联系人id

    //标注为不自动创建列
    @Transient
    private String userName;//乘车人姓名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(Long contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

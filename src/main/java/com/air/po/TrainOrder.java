package com.air.po;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by linux on 2017年03月28日.
 * Time 00:59
 */
@Entity
public class TrainOrder {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;//用户id

    private Float discount;//折扣

    private Long createTime;//下单时间

    private Long trainId;//车次id

    //@Transient注解用户告知hibernate此field不是表属性
    @Transient
    private String passenger;//乘车人
    @Transient
    private String startSite;//起始站
    @Transient
    private String endSite;//终到站
    @Transient
    private float price;//价格
    @Transient
    private String orderTime;//下单时间(字符串形式的)
    @Transient
    private String name;//车次

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

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public String getPassenger() {
        return passenger;
    }

    public void setPassenger(String passenger) {
        this.passenger = passenger;
    }

    public String getStartSite() {
        return startSite;
    }

    public void setStartSite(String startSite) {
        this.startSite = startSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getOrderTime() {
        return this.orderTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(this.createTime));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

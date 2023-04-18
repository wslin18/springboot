package com.example.springboot.model;

import java.util.Date;
import javax.persistence.*;

/**
 * 表名：base_salesman
*/
@Table(name = "base_salesman")
public class SalesMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 手机号码
     */
    private String phone;

    private Date ctime;

    /**
     * 头像图片地址
     */
    private String portrait;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号码
     *
     * @return phone - 手机号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号码
     *
     * @param phone 手机号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return ctime
     */
    public Date getCtime() {
        return ctime;
    }

    /**
     * @param ctime
     */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /**
     * 获取头像图片地址
     *
     * @return portrait - 头像图片地址
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置头像图片地址
     *
     * @param portrait 头像图片地址
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
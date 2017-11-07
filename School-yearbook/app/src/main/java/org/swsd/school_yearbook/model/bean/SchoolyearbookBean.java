package org.swsd.school_yearbook.model.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * author     :  张昭锡
 * time       :  2017/11/04
 * description:  数据库建立的javabean文件
 * version:   :  1.0
 */

public class SchoolyearbookBean extends DataSupport implements Serializable{
    private int id;
    private String name;
    private String address;
    private String phone;
    private String wechat;
    private String email;
    private String qq;
    private String signature;
    private String avatarPath;

    public SchoolyearbookBean(int id,String name,String address,String phone,String wechat,
                              String email, String qq,String signature,String avatarPath){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.wechat = wechat;
        this.email = email;
        this.qq = qq;
        this.signature = signature;
        this.avatarPath=avatarPath;
    }

    public SchoolyearbookBean(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
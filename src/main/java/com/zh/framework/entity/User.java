package com.zh.framework.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by Letg4 on 2017/8/30.
 */
public class User {
    private String id;
    private String uName;
    private String uPassword;
    private int deleteStatus;
    private String uDescription;
//    private String createUserId;
    private User createUser;
    private Date createTime;
    private Date uLastOnLine;
    private List<Role> roles;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getuDescription() {
        return uDescription;
    }

    public void setuDescription(String uDescription) {
        this.uDescription = uDescription;
    }

//    public String getCreateUserId() {
//        return createUserId;
//    }
//
//    public void setCreateUserId(String createUserId) {
//        this.createUserId = createUserId;
//    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getuLastOnLine() {
        return uLastOnLine;
    }

    public void setuLastOnLine(Date uLastOnLine) {
        this.uLastOnLine = uLastOnLine;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

}

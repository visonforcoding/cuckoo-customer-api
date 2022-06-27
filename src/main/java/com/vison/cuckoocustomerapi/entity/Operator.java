package com.vison.cuckoocustomerapi.entity;


import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Table(appliesTo = "operator", comment = "坐席表")
public class Operator extends BaseEntity {

    @Column(columnDefinition = "varchar(50) DEFAULT '' NOT NULL COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "varchar(10) DEFAULT '' NOT NULL COMMENT '工号'")
    private String workNumber;

    @Column(columnDefinition = "varchar(50) DEFAULT '' NOT NULL COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(350) DEFAULT '' NOT NULL COMMENT '头像'")
    private String cover;

    @Column(columnDefinition = "varchar(50) DEFAULT '' NOT NULL COMMENT '真实姓名'")
    private String nickname;

    @Column(columnDefinition = "varchar(64) DEFAULT '' NOT NULL COMMENT '会话ID'")
    private String chatId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}

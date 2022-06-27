package com.vison.cuckoocustomerapi.entity;

import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
@Table(appliesTo = "user", comment = "用户表")
public class User extends BaseEntity {

    @NotBlank(message = "账号不可为空")
    @Column(columnDefinition = "varchar(50) DEFAULT '' NOT NULL COMMENT '用户名'")
    private String username;

    @Column(columnDefinition = "varchar(300) DEFAULT '' NOT NULL COMMENT '封面地址'")
    private String cover;

    @NotBlank(message = "昵称不可为空")
    @Column(columnDefinition = "varchar(50) DEFAULT '' NOT NULL COMMENT '昵称'")
    private String nickname;

    @NotBlank(message = "密码不可为空")
    @Column(columnDefinition = "varchar(64) DEFAULT '' NOT NULL COMMENT '密码'")
    private String password;

    @Column(columnDefinition = "varchar(64) DEFAULT '' NOT NULL COMMENT '会话id'")
    private String chatId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}

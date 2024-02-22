package com.example.demo.entity;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

/**
 * ユーザー情報 Entity
 */
@Data
public class UserInfo implements Serializable {
    /**
     * ID
     */
    private Integer id;
    /**
     * 名前
     */
    private String name;
    /**
     * パスワード
     */
    private String password;
    /**
     * 住所
     */
    private String address;
    /**
     * 電話番号
     */
    private String phone;
    /**
     * 更新日時
     */
    private Date updateDate;
    /**
     * 登録日時
     */
    private Date createDate;
    /**
     * 削除日時
     */
    private Date deleteDate;
}
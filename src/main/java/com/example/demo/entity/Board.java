package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import lombok.Data;

/**
/**
 * 掲示板情報 Entity
 */
@Data
@EntityScan
public class Board implements Serializable {
    /**
     * ID
     */
    private Long id;
    /**
     * タイトル
     */
    private String title;
    /**
     * 内容
     */
    private String body;
    /**
     * 作成者
     */
    private String author;
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
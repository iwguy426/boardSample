package com.example.demo.dto;

import java.io.Serializable;

import lombok.Data;
/**
 * 掲示板情報 検索用リクエストデータ
 */
@Data
public class BoardSearchRequest implements Serializable {
    /**
     * 掲示板ID
     */
    private Long id;
    /**
     * 作成者名
     */
    private String author;
}

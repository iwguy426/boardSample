package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardAddRequest;
import com.example.demo.dto.BoardSearchRequest;
import com.example.demo.dto.BoardUpdateRequest;
import com.example.demo.entity.Board;

/**
 * 掲示板情報 Mapper
 */
@Mapper
public interface BoardMapper {
    /**
     * 掲示板情報全件検索
     * @param board 検索用リクエストデータ
     * @return 検索結果
     */
    List<Board> findAll();
    /**
     * 掲示板情報主キー検索
     * @param id 主キー
     * @return 検索結果
     */
    Board findById(Long id);
    /**
     * 掲示板情報検索
     * @param board 検索用リクエストデータ
     * @return 検索結果
     */
    List<Board> search(BoardSearchRequest board);
    /**
     * 掲示板情報登録
     * @param boardAddRequest 登録用リクエストデータ
     */
    void save(BoardAddRequest boardAddRequest);
    /**
     * 掲示板情報更新
     * @param boardUpdateRequest 更新用リクエストデータ
     */
    void update(BoardUpdateRequest boardUpdateRequest);
    /**
     * 掲示板情報の論理削除
     * @param id ID
     */
    void delete(Long id);
}

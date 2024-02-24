package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardMapper;
import com.example.demo.dto.BoardAddRequest;
import com.example.demo.dto.BoardSearchRequest;
import com.example.demo.dto.BoardUpdateRequest;
import com.example.demo.entity.Board;

 /**
 * 掲示板情報 Service
 */
@Service
public class BoardService {
    /**
     * 掲示板情報 Mapper
     */
   @Autowired
    private BoardMapper boardMapper;
    /**
     * 掲示板情報全件検索
     * @return 検索結果
     */
    public List<Board> findAll() {
        return boardMapper.findAll();
    }
    /**
     * 掲示板情報主キー検索
     * @return 検索結果
     */
    public Board findById(Long id) {
        return boardMapper.findById(id);
    }
    /**
     * 掲示板情報検索
     * @param boardSearchRequest リクエストデータ
     * @return 検索結果
     */
    public List<Board> search(BoardSearchRequest boardSearchRequest) {
        return boardMapper.search(boardSearchRequest);
    }
    /**
     * 掲示板情報登録
     * @param boardAddRequest リクエストデータ
     */
    public void save(BoardAddRequest boardAddRequest) {
        boardMapper.save(boardAddRequest);
    }
    /**
     * 掲示板情報更新
     * @param boardUpdateRequest リクエストデータ
     */
    public void update(BoardUpdateRequest boardUpdateRequest) {
        boardMapper.update(boardUpdateRequest);
    }
    /**
     * 掲示板情報論理削除
     * @param id
     */
    public void delete(Long id) {
        boardMapper.delete(id);
    }
}
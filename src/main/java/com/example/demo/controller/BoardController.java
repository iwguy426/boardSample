package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.dto.BoardAddRequest;
import com.example.demo.dto.BoardSearchRequest;
import com.example.demo.dto.BoardUpdateRequest;
import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.entity.Board;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.BoardService;
import com.example.demo.service.UserInfoService;



/**
 * 掲示板情報 Controller
 */
@Controller
public class BoardController {
    /**
     * ユーザ情報 Service
     */
    @Autowired
    private UserInfoService userInfoService;
    /**
     * 掲示板情報 Service
     */
    @Autowired
    private BoardService boardService;
    /**
     * 掲示板情報一覧画面を表示
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @GetMapping(value = "/board/list")
    public String displayList(Model model) {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boardlist", boardList);
        //model.addAttribute("BoardSearchRequest", new BoardSearchRequest());
        return "board/list";
    }
    /**
     * 掲示板新規登録画面を表示
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @GetMapping(value = "/board/add")
    public String displayAdd(Model model) {
        model.addAttribute("boardAddRequest", new BoardAddRequest());
        return "board/add";
    }
    /**
     * 掲示板編集画面を表示
     * @param id 掲示板ID
     * @param model Model
     * @return 掲示板編集画面
     */
    @GetMapping("/board/{id}/edit")
    public String displayEdit(@PathVariable("id") Long id, Model model) {
        
    	Board boardData = boardService.findById(id);
        model.addAttribute("boardData", boardData);
       
        return "board/edit";
    }
    /**
     * 掲示板検索画面を表示
     * @param model Model
     * @return 掲示板検索画面
     */
    @RequestMapping(value = "/board/list")
    public String displaySearch(Model model) {
        
    	List<Board> searchList = boardService.findAll();
        model.addAttribute("searchlist", searchList);
       
        return "board/search";
    }
    /**
     * 掲示板情報検索
     * @param boardSearchRequest リクエストデータ
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String search(@Validated @ModelAttribute UserLoginRequest userLoginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/login";
        }
        List<UserInfo> userList = userInfoService.nameSearch(userLoginRequest);
        //ログインIDが存在しない場合のエラー表示
        if(userList.size()==0) {
            model.addAttribute("validationError", "お名前またはパスワードが間違います。");
            return "user/login";
        }
        BoardSearchRequest boardSearchRequest = new BoardSearchRequest();
        boardSearchRequest.setAuthor(userLoginRequest.getName());
        List<Board> searchList = boardService.search(boardSearchRequest);
        //掲示板情報がない場合のエラー表示
        if(searchList.size()==0) {
            model.addAttribute("validationError", "掲示板情報がありません。掲示板を新規に登録してください。");
            model.addAttribute("searchlist", null);
        }
        else {
            model.addAttribute("searchlist", searchList);
        }
        model.addAttribute("boardSearchRequest", boardSearchRequest);
        return "board/search";
    }
    /**
     * 掲示板情報検索
     * @param boardSearchRequest リクエストデータ
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @RequestMapping(value = "/board/search", method = RequestMethod.POST)
    public String search(@ModelAttribute BoardSearchRequest boardSearchRequest, Model model) {
        List<Board> boardList = boardService.search(boardSearchRequest);
        model.addAttribute("searchlist", boardList);
        return "board/search";
    }
    /**
     * 掲示板情報削除（論理削除）
     * @param id 掲示板ID
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @GetMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model) {
        // 掲示板情報の削除
        boardService.delete(id);
        //掲示板検索画面へ遷移するため
        List<Board> boardList = boardService.findAll();
        model.addAttribute("searchlist", boardList);
        //掲示板の検索画面のInputボックスにIDと作成者情報を非表示
        model.addAttribute("boardSearchRequest", new BoardSearchRequest());

        return "board/search";
    }
    /**
     * 掲示板新規登録
     * @param boardRequest リクエストデータ
     * @param model Model
     * @return 掲示板情報一覧画面
     */
    @RequestMapping(value = "/board/create", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute BoardAddRequest boardAddRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "board/add";
        }
        //作成者がユーザIDを持っているかをチェック
        //String name = boardAddRequest.getAuthor();
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setName(boardAddRequest.getAuthor());
        List<UserInfo> userList = userInfoService.search(userSearchRequest);
        //ユーザIDがない場合のエラー処理
        if(userList.size()==0) {
            model.addAttribute("validationError", "先にユーザIDを登録してください。");
            model.addAttribute("userAddRequest", new UserAddRequest());
            return "user/add";
        }

        // 掲示板情報の登録
        boardService.save(boardAddRequest);
        //掲示板検索画面へ遷移するため
        List<Board> boardList = boardService.findAll();
        model.addAttribute("searchlist", boardList);
        //掲示板の検索画面のInputボックスにIDと作成者情報を非表示
        model.addAttribute("boardSearchRequest", new BoardSearchRequest());

        return "board/search";
    }
    /**
     * 掲示板更新
     * @param boardRequest リクエストデータ
     * @param model Model
     * @return 掲示板情報詳細画面
     */
    @RequestMapping(value = "/board/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute BoardUpdateRequest boardUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "board/edit";
        }
        //作成者がユーザIDを持っているかをチェック
        //String name = boardAddRequest.getAuthor();
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        userSearchRequest.setName(boardUpdateRequest.getAuthor());
        List<UserInfo> userList = userInfoService.search(userSearchRequest);
        //ユーザIDがない場合のエラー処理
        if(userList.size()==0) {
            model.addAttribute("validationError", "更新する作成者が存在しないので、先にユーザIDを登録してください。");
            UserAddRequest userAddRequest = new UserAddRequest();
            userAddRequest.setName(boardUpdateRequest.getAuthor());
            model.addAttribute("userAddRequest", userAddRequest);
            return "user/add";
        }

        // 掲示板情報の更新
        boardService.update(boardUpdateRequest);
        //更新後の掲示板の全件検索
        List<Board> boardList = boardService.findAll();
        model.addAttribute("searchlist", boardList);
        
        //掲示板の検索画面のInputボックスにIDと作成者情報を表示
        //BoardSearchRequest boardSearchRequest = new BoardSearchRequest();
        //boardSearchRequest.setId(boardUpdateRequest.getId());
        //boardSearchRequest.setAuthor(boardUpdateRequest.getAuthor());
        //model.addAttribute("boardSearchRequest", boardSearchRequest);
        
        //掲示板の検索画面のInputボックスにIDと作成者情報を非表示
        model.addAttribute("boardSearchRequest", new BoardSearchRequest());

        return "board/search";
    }
   
}
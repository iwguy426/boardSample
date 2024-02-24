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

import com.example.demo.dto.UserAddRequest;
import com.example.demo.dto.UserLoginRequest;
import com.example.demo.dto.UserSearchRequest;
import com.example.demo.dto.UserUpdateRequest;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;

/**
 * ユーザー情報 Controller
 */
@Controller
public class UserInfoController {
    /**
     * ユーザー情報 Service
     */
    @Autowired
    private UserInfoService userInfoService;
    /**
     * ユーザー情報一覧画面を表示
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping(value = "/user/list")
    public String displayList(Model model) {
        List<UserInfo> userList = userInfoService.findAll();
        model.addAttribute("userlist", userList);
        model.addAttribute("userSearchRequest", new UserSearchRequest());
        return "user/search";
    }
    /**
     * ユーザー新規登録画面を表示
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("userAddRequest", new UserAddRequest());
        return "user/add";
    }
    /**
     * ユーザー編集画面を表示
     * @param id ユーザーID
     * @param model Model
     * @return ユーザー編集画面
     */
    @GetMapping("/user/{id}/edit")
    //パラメータエラー対応：@PathVariableに("id")を追加
    public String displayEdit(@PathVariable("id") Integer id, Model model) {
        UserInfo user = userInfoService.findById(id);
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getId());
        userUpdateRequest.setName(user.getName());
        userUpdateRequest.setPassword(user.getPassword());
        userUpdateRequest.setPhone(user.getPhone());
        userUpdateRequest.setAddress(user.getAddress());
        model.addAttribute("userUpdateRequest", userUpdateRequest);
        return "user/edit";
    }
    /**
     * ユーザー情報検索
     * @param userSearchRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/user/search", method = RequestMethod.POST)
    public String search(@ModelAttribute UserSearchRequest userSearchRequest, Model model) {
        List<UserInfo> userList = userInfoService.search(userSearchRequest);
        model.addAttribute("userlist", userList);
        return "user/search";
    }
    /**
     * ユーザー情報削除（論理削除）
     * @param id ユーザーID
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @GetMapping("/user/{id}/delete")
    //パラメータエラー対応：@PathVariableに("id")を追加
    public String delete(@PathVariable("id") Integer id, Model model) {
        // ユーザー情報の削除
        userInfoService.delete(id);
        return "redirect:/user/list";
    }
    /**
     * ユーザー新規登録
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String create(@Validated @ModelAttribute UserAddRequest userRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/add";
        }
        // ユーザー情報の登録
        userInfoService.save(userRequest);
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "user/login";
    }
    /**
     * ユーザー更新
     * @param userRequest リクエストデータ
     * @param model Model
     * @return ユーザー情報詳細画面
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute UserUpdateRequest userUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/edit";
        }
        // ユーザー情報の更新
        userInfoService.update(userUpdateRequest);
        return "redirect:/user/login";
    }
    //Added by SEO(2024/02/21)
    /**
     * ログイン画面を表示
     * @param model Model
     * @return ログイン画面
     */
    @GetMapping(value = "/login")
    public String displayLogin(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "user/login";
    }

    /**
     * ログインユーザの情報を表示
     * @param model Model
     * @return ユーザー情報一覧画面
     */
    /*
     @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String searchUser(@Validated @ModelAttribute UserLoginRequest userLoginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力チェックエラーの場合
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/login";
        }
        String name = userLoginRequest.getName();
        List<UserInfo> nameList = userInfoService.nameSearch(name);
        UserSearchRequest userSearchRequest = new UserSearchRequest();
        //userSearchRequest.setId("");
        userSearchRequest.setName(name);
        model.addAttribute("userlist", nameList);
        model.addAttribute("userSearchRequest", userSearchRequest);
        return "user/search";
    } */
    //Added by SEO(2024/02/22)
    /**
     * ログアウト処理
     * @param model Model
     * @return ログイン画面
     */
     @GetMapping(value = "/logout")
    public String logout(Model model) {
        model.addAttribute("userLoginRequest", new UserLoginRequest());
        return "user/login";
    }

    
}

package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLoginRequest implements Serializable {
    /**
     * 名前
     */
    @NotEmpty(message = "名前を入力してください")
    @Size(max = 20, message = "名前は20桁以内で入力してください")
    private String name;
    
    /**
     * パスワード
     */
    @NotEmpty(message = "パスワードを入力してください")
    @Size(max = 20, message = "パスワードは20桁以内で入力してください")
    private String password;
}

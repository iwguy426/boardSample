package com.example.demo.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 掲示板情報 リクエストデータ
 */
@Data
public class BoardAddRequest implements Serializable {
  /**
    * 掲示板ID
	*/
	private Long id;
  /**
   * タイトル
   */
  @NotEmpty(message = "タイトルを入力してください")
  @Size(max = 50, message = "タイトルは50文字以内で入力してください")
  private String title;
  /**
   * 内容
   */
  @NotEmpty(message = "内容を入力してください")
  @Size(max = 125, message = "内容は125文字以内で入力してください")
  private String body;
  /**
   * 作成者
   */
  @NotEmpty(message = "お名前を入力してください")
  @Size(max = 20, message = "作成者は20文字以内で入力してください")
  private String author;

}

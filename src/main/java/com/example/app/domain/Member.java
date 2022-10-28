package com.example.app.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.example.app.validation.AddGroup;
import com.example.app.validation.LoginGroup;

import lombok.Data;

@Data
public class Member {
	private Integer id;

	@NotBlank(groups = { AddGroup.class })
	@Size(max = 10, groups = { AddGroup.class })
	private String name;

	@NotBlank(groups = { AddGroup.class })
	@Size(max = 45, groups = { AddGroup.class })
	//2次開発追加
	@Pattern(regexp = "^[ア-ヴ][ア-ヴ　]+[ア-ヴ]+$", groups = { AddGroup.class }, message = "全角カタカナで入力してください")
	//↑ここまで
	private String furigana;

	@NotBlank(groups = { AddGroup.class, LoginGroup.class })
	@Size(max = 255, groups = { AddGroup.class })
	@Email(groups = { AddGroup.class })
	private String email;

	@NotBlank(groups = { AddGroup.class, LoginGroup.class })
	//2次開発追加、変更（先頭文字はアルファベット指定）
	@Length(min = 6, max = 10, groups = { AddGroup.class })
	@Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9]+$", groups = { AddGroup.class })
	//↑ここまで
	private String password;

	//2次開発追加
	@NotBlank(groups = { AddGroup.class})
	@Length(min = 6, max = 10, groups = { AddGroup.class })
	@Pattern(regexp = "^[a-zA-Z0-9]+$", groups = { AddGroup.class })
	//↑ここまで
	private String password2;

	private String status;

	private Orders orders;

}

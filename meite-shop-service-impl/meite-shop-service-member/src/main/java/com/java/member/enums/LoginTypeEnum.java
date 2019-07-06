package com.java.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
	IOS("IOS"),
	Android("Android"),
	PC("PC"),
	;

	private String type;
}

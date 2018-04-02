package com.github.binarywang.demo.spring.dialog;

public class BaseDialog {

	protected String getDialog(String dialog, String... param) {
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				dialog = dialog.replace("${" + i + "}", param[i]);
			}
		}
		return dialog;
	}
}

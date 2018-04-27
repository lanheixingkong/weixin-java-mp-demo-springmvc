package com.github.binarywang.demo.spring.entity.jieyouzahuodian;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AddContent implements Serializable {
	private static final long serialVersionUID = 5970217355444486406L;

	private Long id;
	private Long counselingId;
	private String content;
	private Date createdate;
	private String userId;
	private int userType;

}

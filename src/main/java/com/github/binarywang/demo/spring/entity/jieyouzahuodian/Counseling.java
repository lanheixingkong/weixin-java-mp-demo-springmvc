package com.github.binarywang.demo.spring.entity.jieyouzahuodian;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Counseling implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -493167878776487040L;
	private Long id;
	private String fromUserId;
	private String fromUserName;
	private String toUserId;
	private String toUserName;
	private String question;
	private String answer;
	private int state;
	private Date createdate;
	private Date answerdate;
	private Long relId;
	
}

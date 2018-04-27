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
	private int state;//1 提问 2 待回答 3 回答 4 追问 5 追答 6 结束
	private Date createdate;
	private Date answerdate;
	private Date waitdate;
	private Long relId;
	
}

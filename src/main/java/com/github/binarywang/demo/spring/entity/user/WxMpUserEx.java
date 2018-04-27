package com.github.binarywang.demo.spring.entity.user;

import java.util.Date;

import lombok.Data;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

@Data
public class WxMpUserEx extends WxMpUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8099793005410424630L;
	
	private Date createdate;

}

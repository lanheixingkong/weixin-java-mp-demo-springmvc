package com.github.binarywang.demo.spring.entity.message.category;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

@Data
@ToString
public class WxMpTextMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4563226366494271567L;

	private String toUser;
	private String fromUser;
	private Long createTime;
	private String msgType;
	private String content;
	private Long msgId;
	private Date createdate;
	
	public WxMpTextMessage(){
	}
	
	public WxMpTextMessage(WxMpXmlMessage wxMessage){
		this.toUser = wxMessage.getToUser();
		this.fromUser = wxMessage.getFromUser();
		this.createTime = wxMessage.getCreateTime();
		this.msgType = wxMessage.getMsgType();
		this.content = wxMessage.getContent();
		this.msgId = wxMessage.getMsgId();
	}
}

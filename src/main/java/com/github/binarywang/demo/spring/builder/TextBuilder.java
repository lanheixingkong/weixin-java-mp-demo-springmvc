package com.github.binarywang.demo.spring.builder;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.service.WeixinService;

/**
 * 
 * @author Binary Wang
 *
 */
public class TextBuilder extends AbstractBuilder {

	@Override
	public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WeixinService service) {
		WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(content).fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
		return m;
	}

	public WxMpXmlOutMessage build(String content, WxMpTextMessage wxMessage, WeixinService service) {
		WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content(content).fromUser(wxMessage.getToUser())
				.toUser(wxMessage.getFromUser()).build();
		return m;
	}
}

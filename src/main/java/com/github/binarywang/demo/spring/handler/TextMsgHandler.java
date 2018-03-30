package com.github.binarywang.demo.spring.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.async.SaveDBAsync;
import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.service.WeixinService;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.category.WxMpTextMessage;

/**
 * @author Binary Wang
 */
@Component
public class TextMsgHandler extends AbstractHandler {
	@Resource
	private SaveDBAsync saveDBAsync;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		WeixinService weixinService = (WeixinService) wxMpService;

		WxMpTextMessage msg = new WxMpTextMessage(wxMessage);
		saveDBAsync.saveWxMpTextMessage(msg);


		return new TextBuilder().build(msg.getContent(), wxMessage, weixinService);

	}

}

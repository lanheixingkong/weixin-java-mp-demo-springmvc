package com.github.binarywang.demo.spring.processor.text;

import java.util.Map;

import me.chanjar.weixin.common.session.WxSession;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import com.github.binarywang.demo.spring.builder.TextBuilder;
import com.github.binarywang.demo.spring.constants.text.JieYouZaHuoDianState;
import com.github.binarywang.demo.spring.constants.text.KeyWord;
import com.github.binarywang.demo.spring.constants.text.WxSessionAttributeKey;
import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.service.WeixinService;

public class JieYouZaHuoPuProcessor extends AbstractTextProcessor {

	private String welcom = "欢迎来到解忧杂货店，回复1，成为店铺老板替人解忧；回复2，作为烦恼者写信倾诉你的烦恼";

	@Override
	public WxMpXmlOutMessage processor(WxMpTextMessage msg, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String content = msg.getContent();

		if (KeyWord.JIE_YOU_ZA_HUO_DIAN.equals(content)) {
			WxSession session = sessionManager.getSession(msg.getFromUser());
			session.setAttribute(WxSessionAttributeKey.JIE_YOU_ZA_HUO_DIAN_STATE, JieYouZaHuoDianState.INTRO);

			return new TextBuilder().build(welcom, msg, (WeixinService) wxMpService);
		}
		return null;
	}

}

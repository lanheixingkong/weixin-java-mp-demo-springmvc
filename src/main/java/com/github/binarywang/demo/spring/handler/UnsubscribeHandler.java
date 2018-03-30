package com.github.binarywang.demo.spring.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.mapper.user.WxMpUserMapper;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class UnsubscribeHandler extends AbstractHandler {
	@Resource
	private WxMpUserMapper wxMpUserMapper;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {
		String openId = wxMessage.getFromUser();
		this.logger.info("取消关注用户 OPENID: " + openId);
		// TODO 可以更新本地数据库为取消关注状态
		wxMpUserMapper.unsubscribe(openId);

		return null;
	}

}

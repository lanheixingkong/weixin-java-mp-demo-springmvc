package com.github.binarywang.demo.spring.async;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.entity.user.WxMpUserEx;
import com.github.binarywang.demo.spring.mapper.message.WxMpTextMessageMapper;
import com.github.binarywang.demo.spring.mapper.user.WxMpUserMapper;

@Component
public class SaveDBAsync {

	@Resource
	private WxMpUserMapper wxMpUserMapper;

	@Resource
	private WxMpTextMessageMapper wxMpTextMessageMapper;

	@Async
	public void saveWxMpUser(WxMpUserEx user) {
		WxMpUserEx wmu = wxMpUserMapper.selectByOpenId(user.getOpenId());
		if (wmu != null) {
			wxMpUserMapper.update(user);
		} else {
			wxMpUserMapper.insert(user);
		}
	}

	@Async
	public void saveWxMpTextMessage(WxMpTextMessage msg) {
		wxMpTextMessageMapper.insert(msg);
		String openId = msg.getFromUser();
		WxMpUserEx wmu = wxMpUserMapper.selectByOpenId(openId);
		if (wmu == null) {
			WxMpUserEx user = new WxMpUserEx();
			user.setOpenId(openId);
			user.setSubscribe(true);
			wxMpUserMapper.insert(user);
		}
	}
}

package com.github.binarywang.demo.spring.async;

import javax.annotation.Resource;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailSender {

	@Resource
	private JavaMailSender sender;

	@Async
	public void send(String to, String subject, String content) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("519356442@qq.com"); // 发件人邮箱
		msg.setTo(to); // 收件人邮箱
		msg.setSubject(subject); // 标题
		msg.setText(content); // 文本信息
		try {
			sender.send(msg);
			if (log.isDebugEnabled()) {
				log.debug("mail send success, to={}, subject={}, content={}", to, subject, content);
			}
		} catch (MailException e) {
			log.error("mail send fail, to={}, subject={}, content={}", to, subject, content, e);
		}
	}
}

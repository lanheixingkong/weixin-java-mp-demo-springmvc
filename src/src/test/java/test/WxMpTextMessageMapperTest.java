package test;

import javax.annotation.Resource;

import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.binarywang.demo.spring.entity.message.category.WxMpTextMessage;
import com.github.binarywang.demo.spring.mapper.message.WxMpTextMessageMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WxMpTextMessageMapperTest {

	@Resource
	private WxMpTextMessageMapper wxMpTextMessageMapper;

//	@org.junit.Test
//	public void testInsert() {
//		WxMpTextMessage msg = new WxMpTextMessage();
//		msg.setContent("hello world");
//		msg.setCreateTime(System.currentTimeMillis());
//		msg.setFromUser("lei");
//		msg.setToUser("haha");
//		msg.setMsgId(System.currentTimeMillis());
//		msg.setMsgType(XmlMsgType.TEXT);
//		
//		wxMpTextMessageMapper.insert(msg);
//	}

//	@org.junit.Test
//	public void testDelete() {
//		long id = 1L;
//		wxMpTextMessageMapper.delete(id);
//	}
}

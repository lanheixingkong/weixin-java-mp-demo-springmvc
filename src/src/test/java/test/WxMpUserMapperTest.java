package test;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.binarywang.demo.spring.mapper.user.WxMpUserMapper;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class WxMpUserMapperTest {

	@Resource
	private WxMpUserMapper wxMpUserMapper;

	@org.junit.Test
	public void testInsert() {
		WxMpUser data = new WxMpUser();
		data.setOpenId("1234");
		data.setCity("city");
		data.setCountry("country");
		data.setGroupId(1);
		data.setHeadImgUrl("headImgUrl");
		data.setLanguage("language");
		data.setNickname("nickname");
		data.setProvince("province");
		data.setRemark("remark");
		data.setSex("male");
		data.setSexId(1);
		data.setSubscribe(false);
		data.setSubscribeTime(System.currentTimeMillis());
		data.setUnionId("1223445");
		
		wxMpUserMapper.insert(data);
	}

//	@org.junit.Test
//	public void testDelete() {
//		String openId = "1234";
//		wxMpUserMapper.delete(openId);
//	}
}

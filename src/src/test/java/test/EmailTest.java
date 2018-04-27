package test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.binarywang.demo.spring.async.EmailSender;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class EmailTest {

//	@Resource
//	private EmailSender emailSender;
//	
//	@Test
//	public void sendTestEmail() {
//		String to = "519356442@qq.com";
//		String subject = "Email Test";
//		String content = "Hahahahhaha";
//		
//		emailSender.send(to, subject, content);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

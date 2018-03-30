package test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.github.binarywang.demo.spring.entity.test.Test;
import com.github.binarywang.demo.spring.mapper.test.TestMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class TestMapperTest {

	@Resource
	private TestMapper testMapper;

	@org.junit.Test
	public void test() {
		List<Test> list = testMapper.findList();
//		Assert.isTrue(list.isEmpty(), "list must be empty");

		Test test = new Test();
		String name = "test";
		test.setName(name);
		testMapper.insert(test);

		list = testMapper.findList();
		Assert.notEmpty(list, "list must not be empty");
		Assert.notNull(test.getId(), "id must not be null");
		Assert.isTrue(name.equals(list.get(0).getName()), "name must equals " + name);

		name = "hello world";
		test.setName(name);
		testMapper.update(test);
		list = testMapper.findList();
		Assert.isTrue(name.equals(list.get(0).getName()), "name must equals " + name);

//		testMapper.delete(test.getId());
//		list = testMapper.findList();
//		Assert.isTrue(list.isEmpty(), "after delete list must be empty");
	}

}

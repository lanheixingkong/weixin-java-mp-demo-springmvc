package com.github.binarywang.demo.spring.mapper.test;

import java.util.List;

import com.github.binarywang.demo.spring.entity.test.Test;

public interface TestMapper {
	List<Test> findList();
	int insert(Test test);
	int update(Test test);
	int delete(Long id);
}

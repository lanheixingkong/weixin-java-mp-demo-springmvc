package com.github.binarywang.demo.spring.mapper.jieyouzahuodian;

import com.github.binarywang.demo.spring.entity.jieyouzahuodian.AddContent;

public interface AddContentMapper {

	int insert(AddContent addContent);

	AddContent selectById(Long id);
}

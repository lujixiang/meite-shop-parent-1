package com.java.goods.service.impl;

import cn.hutool.core.convert.Convert;
import com.java.core.base.Result;
import com.java.goods.dao.ProductReposiory;
import com.java.goods.entity.ProductEntity;
import com.java.goods.service.ProductSearchService;
import com.java.member.output.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: jiangli
 * @Date: 2019/6/26 15:49
 */
@RestController
public class ProductSearchServiceImpl implements ProductSearchService {
	@Autowired
	private ProductReposiory productReposiory;

	@Override
	public Result<List<ProductDto>> search(String name) {
//		int i = 1/0;
		List<ProductEntity> productEntityList = productReposiory.findAllByName(name);
		List<ProductDto> productDtoList = Convert.toList(ProductDto.class,productEntityList);
		return Result.ok(productDtoList);
	}
}
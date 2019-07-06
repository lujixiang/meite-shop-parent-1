package com.java.goods.service;

import com.java.core.base.Result;
import com.java.member.output.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface ProductSearchService {

	@GetMapping("/search")
	Result<List<ProductDto>> search(String name);
}

package com.java.goods.dao;

import com.java.goods.entity.ProductEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ProductReposiory extends ElasticsearchRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByName(String name);

}
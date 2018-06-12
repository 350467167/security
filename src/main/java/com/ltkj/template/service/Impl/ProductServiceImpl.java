package com.ltkj.template.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ltkj.template.model.Product;
import com.ltkj.template.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@CacheConfig(cacheNames = "product")
@Slf4j
public class ProductServiceImpl {
	@Autowired
	private ProductRepository productRepository;

	@Cacheable(key = "#p0")
	public Product findById(String id) {
		log.info("***** findById ***** id : " + id);
		return productRepository.findById(id);
	}

	@CachePut(key = "#p0")
	public Product save(String id, Product product) throws Exception {
		log.info("***** save ***** id : " + id);
		return productRepository.save(product);
	}

	public List<Product> save(List<Product> list) throws Exception {
		return productRepository.save(list);
	}

	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

	public List<Product> queryAll() throws Exception {
		return productRepository.findAll();
	}

	public void delete(String id) {
		productRepository.delete(id);
	}
}

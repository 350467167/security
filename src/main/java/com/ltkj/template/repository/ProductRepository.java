package com.ltkj.template.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ltkj.template.model.Product;

//@RepositoryRestResource(collectionResourceRel = "product", path = "api/product")
public interface ProductRepository extends MongoRepository<Product, String> {
	public Product findById(String id);

	public Product findByName(String name);

	public List<Product> findByNameLike(String name);

}
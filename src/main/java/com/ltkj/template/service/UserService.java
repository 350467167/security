package com.ltkj.template.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltkj.template.dbconfig.DynamicDataSourceAspect.TargetDataSource;
import com.ltkj.template.mapper.UserManMapper;
import com.ltkj.template.model.User;

@Service
public class UserService {
	@Autowired
	private UserManMapper userMapper;

	@TargetDataSource
	public List<User> findAllByDynamicDB(String s) {
		return userMapper.findAll();
	}

	public List<User> findAll() {
		return userMapper.findAll();
	}

	@TargetDataSource
	public void insert(String ds, User user) {
		System.err.println("insert ds user");
		userMapper.insert(user);
	}

	public void insert(User user) {
		System.err.println("insert user");
		userMapper.insert(user);
	}

	public User findOne(String id) {
		return null;
	}

	public User save(User updatedUser) {
		return null;
	}

	public void delete(String id) {
		throw new UnsupportedOperationException();
	}

	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	public User getUserInfo(User user) {
		User u = userMapper.findByUsername(user.getUsername());
		return u;
	}
}

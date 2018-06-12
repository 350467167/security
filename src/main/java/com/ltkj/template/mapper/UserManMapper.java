package com.ltkj.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ltkj.template.model.User;

/**
 * @author zwy
 *
 */
@Mapper
public interface UserManMapper {
	public User findByUsername(String username);

	public void insert(User user);

	public List<String> findRolesByUserID(String userId);

	public List<User> findAll();

	public User getUserInfo(User user);
}

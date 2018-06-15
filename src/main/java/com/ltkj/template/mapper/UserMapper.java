package com.ltkj.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ltkj.template.model.User;

@Mapper
public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> selectUserList(User user);

	int selectUserListCount();

}
package com.ltkj.template.mapper;

import java.util.List;

import com.ltkj.template.model.UserStationRole;
import com.ltkj.template.modelPage.UserStationRoleInfo;

public interface UserStationRoleMapper {
	int insert(UserStationRole record);

	int insertSelective(UserStationRole record);

	List<UserStationRoleInfo> selectByUserId(int userId);

	void deleteByUserId(String id);
}
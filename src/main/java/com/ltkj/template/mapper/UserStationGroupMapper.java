package com.ltkj.template.mapper;

import com.ltkj.template.model.UserStationGroup;

public interface UserStationGroupMapper {
	int insert(UserStationGroup record);

	int insertSelective(UserStationGroup record);
}
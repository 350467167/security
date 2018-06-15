package com.ltkj.template.mapper;

import java.util.List;

import com.ltkj.template.model.SysRole;

public interface SysRoleMapper {
	int deleteByPrimaryKey(Integer roleId);

	int insert(SysRole record);

	int insertSelective(SysRole record);

	SysRole selectByPrimaryKey(Integer roleId);

	int updateByPrimaryKeySelective(SysRole record);

	int updateByPrimaryKey(SysRole record);

	List<SysRole> selectRoleListByStationId(Integer stationId);
}
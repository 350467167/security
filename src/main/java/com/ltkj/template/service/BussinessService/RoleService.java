package com.ltkj.template.service.BussinessService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltkj.template.dbconfig.DynamicDataSourceAspect.TranExtend;
import com.ltkj.template.mapper.SysRoleMapper;
import com.ltkj.template.utility.RestGenerator;
import com.ltkj.template.utility.RestResponse;

@Service
public class RoleService {

	@Autowired
	SysRoleMapper sysRoleMapper;

	@TranExtend
	public RestResponse<?> getRoleList(int stationId) {
		return RestGenerator.successResult(sysRoleMapper.selectRoleListByStationId(stationId));
	}
}

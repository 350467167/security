package com.ltkj.template.service.BussinessService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltkj.template.dbconfig.DynamicDataSourceContextHolder;
import com.ltkj.template.dbconfig.DynamicDataSourceAspect.TranExtend;
import com.ltkj.template.mapper.StationMapper;
import com.ltkj.template.mapper.UserMapper;
import com.ltkj.template.mapper.UserStationRoleMapper;
import com.ltkj.template.model.User;
import com.ltkj.template.modelPage.UserInfo;
import com.ltkj.template.modelPage.UserStationRoleInfo;
import com.ltkj.template.utility.RestGenerator;
import com.ltkj.template.utility.RestResponse;

@Service
public class UserInfoService {
	@Autowired
	UserMapper userMapper;

	@Autowired
	StationMapper stationMapper;

	@Autowired
	UserStationRoleMapper userStationRoleMapper;

	@TranExtend
	public RestResponse<?> getUserList(User user) {
		List<User> userList = userMapper.selectUserList(user);
		int total = userMapper.selectUserListCount();

		return RestGenerator.successResult(userList, total);
	}

	@TranExtend
	public RestResponse<?> saveUser(UserInfo userInfo) {
		User user = userInfo.getUser();
		if (user.getUserId() == null) {
			user.setUserId(userMapper.insert(user));
		} else {
			userMapper.updateByPrimaryKey(user);
			userStationRoleMapper.deleteByUserId(user.getId());
		}

		userInfo.getUserStationRoleInfoList().forEach(record -> {
			record.setSysUserId(user.getUserId());
			userStationRoleMapper.insert(record);
		});

		return RestGenerator.successResult();
	}

	@TranExtend
	public RestResponse<?> removeUser(int userId) {
		userMapper.deleteByPrimaryKey(userId);
		// TODO 删除周边信息

		return RestGenerator.successResult();
	}

	@TranExtend
	public RestResponse<?> getUserInfo(int userId) {
		System.err.println(DynamicDataSourceContextHolder.getAutoCommit());

		UserInfo userInfo = new UserInfo();

		User user = userMapper.selectByPrimaryKey(userId);
		// List<Station> stationList =
		// stationMapper.selectStationListByUserId(userId);
		List<UserStationRoleInfo> userStationRoleList = userStationRoleMapper.selectByUserId(userId);

		// userInfo.setStationList(stationList);
		userInfo.setUser(user);
		userInfo.setUserStationRoleInfoList(userStationRoleList);

		return RestGenerator.successResult(userInfo);
	}
}

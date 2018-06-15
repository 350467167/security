package com.ltkj.template;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ltkj.template.dbconfig.DynamicDataSourceRegister;
import com.ltkj.template.model.Station;
import com.ltkj.template.model.SysRole;
import com.ltkj.template.model.User;
import com.ltkj.template.modelPage.UserInfo;
import com.ltkj.template.modelPage.UserStationRoleInfo;
import com.ltkj.template.service.BussinessService.RoleService;
import com.ltkj.template.service.BussinessService.StationService;
import com.ltkj.template.service.BussinessService.UserInfoService;
import com.ltkj.template.utility.RestResponse;

@EnableTransactionManagement
@Import({ DynamicDataSourceRegister.class })
@MapperScan("com.ltkj.template.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoApplicationTests {
	// @Test
	public void selectAll() throws Exception {
		RestResponse<?> r = users();
		@SuppressWarnings("unchecked")
		List<User> l = (List<User>) r.getResult();
		l.forEach(System.out::println);
	}

	@Autowired
	UserInfoService userInfoService;

	public RestResponse<?> users() {
		return userInfoService.getUserList(new User());
	}

	// @Test
	public void save() throws Exception {
		insertUser();
		updateUser();
	}

	public RestResponse<?> insertUser() {
		User user = new User();
		user.setCompanyId(998998);
		user.setUserCode("007");
		user.setUserName("007");

		UserInfo ui = new UserInfo();
		ui.setUser(user);

		return userInfoService.saveUser(ui);
	}

	public RestResponse<?> updateUser() {
		User user = new User();
		user.setUserId(2);
		user.setCompanyId(998998);
		user.setUserCode("007");
		user.setUserName("007");

		UserInfo ui = new UserInfo();
		ui.setUser(user);

		return userInfoService.saveUser(ui);
	}

	// @Test
	public void delete() {
		userInfoService.removeUser(5);
	}

	// @Test
	public void get() {
		RestResponse<UserInfo> r = (RestResponse<UserInfo>) userInfoService.getUserInfo(10000);

		User user = r.getResult().getUser();
		// List<Station> stationList = r.getResult().getStationList();
		List<UserStationRoleInfo> userStationRoleInfoList = r.getResult().getUserStationRoleInfoList();

		System.err.println(user);
		// stationList.forEach(System.err::println);
		userStationRoleInfoList.forEach(System.err::println);
	}

	@Autowired
	StationService stationService;

	// @Test
	public void getS() {
		List<Station> l = (List<Station>) stationService.getStationList().getResult();

		l.forEach(System.err::println);
	}

	@Autowired
	RoleService roleService;

	@Test
	public void getR() {
		List<SysRole> l = (List<SysRole>) roleService.getRoleList(1283).getResult();

		l.forEach(System.err::println);
	}
}

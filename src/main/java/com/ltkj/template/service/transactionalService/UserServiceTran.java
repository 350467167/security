package com.ltkj.template.service.transactionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltkj.template.dbconfig.DynamicDataSourceAspect.TranExtend;
import com.ltkj.template.model.User;
import com.ltkj.template.service.UserService;

@Service
public class UserServiceTran {
	@Autowired
	UserService userService;

	@TranExtend
	public void insertTranExtend(String ds) {
		System.err.println("insertTranExtend");
		User user = new User();
		user.setEmail("email");
		user.setId("insertTranExtend-1");
		user.setPassword("password");
		user.setUsername("username");

		User user2 = new User();
		user2.setEmail("email");
		user2.setId("insertTranExtend-2");
		user2.setPassword("password");
		user2.setUsername("username");

		userService.insert(ds, user);
		userService.insert(user);
		userService.insert(ds, user2);

		user.setUsername("username-update");
		userService.save(user);

		throw new RuntimeException("测试roll back");
	}

	@TranExtend
	public void insertTranExtendWithoutError(String ds) {
		System.err.println("insertTranExtendWithoutError");
		User user = new User();
		user.setEmail("email");
		user.setId("insertTranExtendWithoutError-1");
		user.setPassword("password");
		user.setUsername("username");

		User user2 = new User();
		user2.setEmail("email");
		user2.setId("insertTranExtendWithoutError-2");
		user2.setPassword("password");
		user2.setUsername("username");

		userService.insert(ds, user);
		userService.insert(user);
		userService.insert(ds, user2);

		user.setUsername("username-update");
		userService.save(user);
	}

	public void insertWithoutTranExtend(String ds) {
		System.err.println("insertWithoutTranExtend");
		User user = new User();
		user.setEmail("email");
		user.setId("insertWithoutTranExtend-1");
		user.setPassword("password");
		user.setUsername("username");

		User user2 = new User();
		user2.setEmail("email");
		user2.setId("insertWithoutTranExtend-2");
		user2.setPassword("password");
		user2.setUsername("username");

		userService.insert(ds, user);
		userService.insert(user);
		userService.insert(ds, user2);

		user.setUsername("username-update");
		userService.save(user);

		throw new RuntimeException("测试roll back");
	}
}

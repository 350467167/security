package com.ltkj.template.service.transactionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ltkj.template.model.User;
import com.ltkj.template.service.UserService;

@Service
public class UserServiceTran {
	@Autowired
	UserService userService;

	@Transactional
	public void insert(String ds, User user) {

		userService.insert(ds, user);
		userService.insert(user);
		throw new RuntimeException("xxx");
	}
}

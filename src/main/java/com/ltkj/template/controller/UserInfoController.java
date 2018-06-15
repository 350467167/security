package com.ltkj.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ltkj.template.model.User;
import com.ltkj.template.modelPage.UserInfo;
import com.ltkj.template.service.BussinessService.UserInfoService;
import com.ltkj.template.utility.RestResponse;

@RestController
@RequestMapping("/api/")
public class UserInfoController {
	@Autowired
	UserInfoService userInfoService;

	@RequestMapping(value = "users", method = RequestMethod.POST)
	public RestResponse<?> users(@RequestBody User user) {
		return userInfoService.getUserList(user);
	}

	@RequestMapping(value = "user", method = RequestMethod.POST)
	public RestResponse<?> user(@RequestBody UserInfo userInfo) {
		return userInfoService.saveUser(userInfo);
	}

	@RequestMapping(value = "getUserInfo/{id}", method = RequestMethod.GET)
	public RestResponse<?> getUserInfo(@PathVariable int id) {
		return userInfoService.getUserInfo(id);
	}

}

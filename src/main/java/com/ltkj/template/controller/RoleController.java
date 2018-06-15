package com.ltkj.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ltkj.template.service.BussinessService.RoleService;
import com.ltkj.template.utility.RestResponse;

@RestController
@RequestMapping("/api/")
public class RoleController {
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "getRoles/{id}", method = RequestMethod.GET)
	public RestResponse<?> getRoles(@PathVariable int id) {
		return roleService.getRoleList(id);
	}
}

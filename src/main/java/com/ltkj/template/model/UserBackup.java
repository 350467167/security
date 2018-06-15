package com.ltkj.template.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author zwy
 */
@Data
public class UserBackup {
	private String id;
	private String username;
	private String password;
	private String email;
	private Date lastPasswordResetDate;

	private List<String> roles;
}

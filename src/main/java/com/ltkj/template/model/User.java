package com.ltkj.template.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * @author zwy
 */
@Data
public class User implements Serializable{
	private static final long serialVersionUID = -5343634299013222046L;
	
	private String id;
	private String username;
	private String password;
	private String email;
	private Date lastPasswordResetDate;
	
	private List<String> roles;
}

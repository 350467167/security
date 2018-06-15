package com.ltkj.template.modelPage;

import com.ltkj.template.model.UserStationRole;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserStationRoleInfo extends UserStationRole {
	private String stationName;
	private String roleName;
}
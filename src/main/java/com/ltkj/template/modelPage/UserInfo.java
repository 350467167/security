package com.ltkj.template.modelPage;

import java.io.Serializable;
import java.util.List;

import com.ltkj.template.model.User;

import lombok.Data;

@Data
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	// private List<Station> stationList;
	private List<UserStationRoleInfo> userStationRoleInfoList;

}
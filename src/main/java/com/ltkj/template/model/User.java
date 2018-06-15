package com.ltkj.template.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends PageInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String userCode;

	private String userName;

	private String userDepartment;

	private String userDuty;

	private Integer userState;

	private String userRelation;

	private String userLoginName;

	private String userLoginPass;

	private String userRemark;

	private Integer userType;

	private Integer userValue;

	private Integer userStationId;

	private Integer userPhotoId;

	private Integer companyId;

	private String userEmail;

	private String userPhone;

	private String userMobilePhone;

	private Integer userSex;

	private String userInspectFlag;

	private Date userBeginDate;

	private Date userEndDate;

	private Integer userVisibleFlag;

}
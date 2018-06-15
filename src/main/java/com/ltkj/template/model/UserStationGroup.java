package com.ltkj.template.model;

import java.io.Serializable;
import java.util.Date;

public class UserStationGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer userid;

	private Integer stationId;

	private Integer grouptype;

	private String groupname;

	private Date addtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public Integer getGrouptype() {
		return grouptype;
	}

	public void setGrouptype(Integer grouptype) {
		this.grouptype = grouptype;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname == null ? null : groupname.trim();
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
}
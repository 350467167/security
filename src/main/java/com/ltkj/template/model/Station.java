package com.ltkj.template.model;

import java.io.Serializable;

public class Station implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer stationId;

	private String stationName;

	private String simpleName;

	private Integer isVisible;

	private Integer initFlag;

	private String stationSmallPicture;

	private String stationMiddlePicture;

	private String stationLargePicture;

	private String stationCode;

	private Integer stationIndex;

	private Integer stationProvince;

	private Double stationLongitude;

	private Double stationLatitude;

	private String stationIntroduce;

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName == null ? null : stationName.trim();
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName == null ? null : simpleName.trim();
	}

	public Integer getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Integer isVisible) {
		this.isVisible = isVisible;
	}

	public Integer getInitFlag() {
		return initFlag;
	}

	public void setInitFlag(Integer initFlag) {
		this.initFlag = initFlag;
	}

	public String getStationSmallPicture() {
		return stationSmallPicture;
	}

	public void setStationSmallPicture(String stationSmallPicture) {
		this.stationSmallPicture = stationSmallPicture == null ? null : stationSmallPicture.trim();
	}

	public String getStationMiddlePicture() {
		return stationMiddlePicture;
	}

	public void setStationMiddlePicture(String stationMiddlePicture) {
		this.stationMiddlePicture = stationMiddlePicture == null ? null : stationMiddlePicture.trim();
	}

	public String getStationLargePicture() {
		return stationLargePicture;
	}

	public void setStationLargePicture(String stationLargePicture) {
		this.stationLargePicture = stationLargePicture == null ? null : stationLargePicture.trim();
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode == null ? null : stationCode.trim();
	}

	public Integer getStationIndex() {
		return stationIndex;
	}

	public void setStationIndex(Integer stationIndex) {
		this.stationIndex = stationIndex;
	}

	public Integer getStationProvince() {
		return stationProvince;
	}

	public void setStationProvince(Integer stationProvince) {
		this.stationProvince = stationProvince;
	}

	public Double getStationLongitude() {
		return stationLongitude;
	}

	public void setStationLongitude(Double stationLongitude) {
		this.stationLongitude = stationLongitude;
	}

	public Double getStationLatitude() {
		return stationLatitude;
	}

	public void setStationLatitude(Double stationLatitude) {
		this.stationLatitude = stationLatitude;
	}

	public String getStationIntroduce() {
		return stationIntroduce;
	}

	public void setStationIntroduce(String stationIntroduce) {
		this.stationIntroduce = stationIntroduce == null ? null : stationIntroduce.trim();
	}
}
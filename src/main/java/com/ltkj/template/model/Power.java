package com.ltkj.template.model;

public class Power extends PowerKey {
    private String powerName;

    private Integer powerStatus;

    private Integer powerType;

    private String powerRemark;

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName == null ? null : powerName.trim();
    }

    public Integer getPowerStatus() {
        return powerStatus;
    }

    public void setPowerStatus(Integer powerStatus) {
        this.powerStatus = powerStatus;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }

    public String getPowerRemark() {
        return powerRemark;
    }

    public void setPowerRemark(String powerRemark) {
        this.powerRemark = powerRemark == null ? null : powerRemark.trim();
    }
}
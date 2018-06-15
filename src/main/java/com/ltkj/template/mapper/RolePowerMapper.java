package com.ltkj.template.mapper;

import com.ltkj.template.model.RolePowerKey;

public interface RolePowerMapper {
    int deleteByPrimaryKey(RolePowerKey key);

    int insert(RolePowerKey record);

    int insertSelective(RolePowerKey record);
}
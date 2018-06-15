package com.ltkj.template.mapper;

import com.ltkj.template.model.Power;
import com.ltkj.template.model.PowerKey;

public interface PowerMapper {
    int deleteByPrimaryKey(PowerKey key);

    int insert(Power record);

    int insertSelective(Power record);

    Power selectByPrimaryKey(PowerKey key);

    int updateByPrimaryKeySelective(Power record);

    int updateByPrimaryKey(Power record);
}
package com.ltkj.template.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ltkj.template.model.Station;

@Mapper
public interface StationMapper {
	int deleteByPrimaryKey(Integer stationId);

	int insert(Station record);

	int insertSelective(Station record);

	Station selectByPrimaryKey(Integer stationId);

	int updateByPrimaryKeySelective(Station record);

	int updateByPrimaryKeyWithBLOBs(Station record);

	int updateByPrimaryKey(Station record);

	List<Station> selectStationListByUserId(int userId);

	List<Station> selectAll();
}
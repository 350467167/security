package com.ltkj.template.service.BussinessService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltkj.template.dbconfig.DynamicDataSourceAspect.TranExtend;
import com.ltkj.template.mapper.StationMapper;
import com.ltkj.template.model.Station;
import com.ltkj.template.utility.RestGenerator;
import com.ltkj.template.utility.RestResponse;

@Service
public class StationService {

	@Autowired
	StationMapper stationMapper;

	@TranExtend
	public RestResponse<?> getStationList() {
		List<Station> l = stationMapper.selectAll();
		return RestGenerator.successResult(l);
	}
}

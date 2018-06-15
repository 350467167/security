package com.ltkj.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ltkj.template.service.BussinessService.StationService;
import com.ltkj.template.utility.RestResponse;

@RestController
@RequestMapping("/api/")
public class StationController {
	@Autowired
	StationService stationService;

	@RequestMapping(value = "stations", method = RequestMethod.GET)
	public RestResponse<?> stations() {
		return stationService.getStationList();
	}
}

package com.ltkj.template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ltkj.template.kafka.producer.TempProducer;
import com.ltkj.template.service.Impl.ProductServiceImpl;
import com.ltkj.template.utility.RestGenerator;
import com.ltkj.template.utility.RestResponse;

@RestController
@RequestMapping("/api/")
public class ProducerController {
	@Autowired
	private TempProducer producer;
	@Autowired
	private ProductServiceImpl productServiceImpl;

	@RequestMapping(value = "sendKafka", method = RequestMethod.GET)
	public RestResponse<?> sendKafka() {
		producer.sendKafka();

		return RestGenerator.successResult("OK");
	}

	@RequestMapping(value = "product/{id}", method = RequestMethod.GET)
	public RestResponse<?> product(@PathVariable String id) {

		return RestGenerator.successResult(productServiceImpl.findById(id));
	}
}

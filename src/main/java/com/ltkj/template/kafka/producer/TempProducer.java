package com.ltkj.template.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.ltkj.template.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TempProducer {
	@Autowired
	@SuppressWarnings("rawtypes")
	private KafkaTemplate kafkaTemplate;

	private static ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public void sendKafka() {
		try {
			for (int i = 0; i < 100; i++) {
				kafkaTemplate.send("test", "name" + Integer.toString(i), Integer.toString(i));
			}
			log.info("***** 发送kafka成功. ***** ");
		} catch (Exception e) {
			log.error("***** 发送kafka失败. *****", e);
		}

		try {
			for (int i = 0; i < 20; i++) {
				Product p = new Product();
				String uuid = UUID.randomUUID().toString();

				p.setId(uuid);
				p.setName("name " + i);
				p.setPrice(10 + i);
				p.setQuantity(20 + i);

				kafkaTemplate.send("test2", "index: " + Integer.toString(i), mapper.writeValueAsString(p));
			}
			log.info("***** 发送kafka成功. ***** ");
		} catch (Exception e) {
			log.error("***** 发送kafka失败. *****", e);
		}
	}
}

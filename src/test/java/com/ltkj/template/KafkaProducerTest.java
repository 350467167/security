package com.ltkj.template;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.ltkj.template.model.Product;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaProducerTest {
	@Autowired
	@SuppressWarnings("rawtypes")
	private KafkaTemplate kafkaTemplate;

	ObjectMapper om = new ObjectMapper();

	@Test
	@SuppressWarnings("unchecked")
	public void sendKafka() throws Exception {
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

				kafkaTemplate.send("test2", "index: " + Integer.toString(i), om.writeValueAsString(p));
			}
			log.info("***** 发送kafka成功. ***** ");
		} catch (Exception e) {
			log.error("***** 发送kafka失败. *****", e);
		}
	}
}

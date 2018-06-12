package com.ltkj.template.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ltkj.template.model.Product;
import com.ltkj.template.service.Impl.ProductServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "kafka.enabled")
public class ConsumerListener {
	@Autowired
	ProductServiceImpl productServiceImpl;

	private static ObjectMapper mapper = new ObjectMapper();

	@KafkaListener(topics = { "test" })
	public void testListener(ConsumerRecord<?, ?> record) {

		log.info("key: " + record.key());
		log.info("value: " + record.value().toString());
	}

	@KafkaListener(topics = { "test2" })
	public void test2Listener(ConsumerRecord<?, ?> record) throws Exception {
		String value = record.value().toString();
		log.info("key: " + record.key());
		log.info("value: " + value);

		Product product = mapper.readValue(value, Product.class);

		productServiceImpl.save(product.getId(), product);
	}
}
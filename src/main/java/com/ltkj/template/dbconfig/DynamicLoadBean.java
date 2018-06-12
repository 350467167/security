package com.ltkj.template.dbconfig;

import java.util.Map;

import javax.sql.DataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DynamicLoadBean implements ApplicationContextAware {
	@Autowired
	DynamicDataSource dynamicDataSource;

	private ObjectMapper om = new ObjectMapper();
	private ConfigurableApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	public ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public void registBean(String dataSourceConnection) {
		DefaultListableBeanFactory fcy = (DefaultListableBeanFactory) applicationContext
				.getAutowireCapableBeanFactory();

		DynamicDataSourceContextHolder.dataSourceConnections.add(dataSourceConnection);

		Map<String, Object> dsMap = null;

		try {
			dsMap = om.readValue(dataSourceConnection, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			DynamicDataSourceContextHolder.clearDataSourceConnection();
			return;
		}

		BeanDefinition beanDefinition = fcy.getBeanDefinition("dataSource");

		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		Map<Object, Object> targetDataSources = (Map<Object, Object>) mpv.getPropertyValue("targetDataSources")
				.getValue();

		DataSource dataSource = DynamicDataSourceRegister.buildDataSource(dsMap);
		DynamicDataSourceRegister.dataBinder(dataSource, null, dynamicDataSource.getConversionService(),
				dynamicDataSource.getDataSourcePropertyValues());
		targetDataSources.put(dataSourceConnection, dataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		fcy.registerBeanDefinition("dataSource", beanDefinition);

		dynamicDataSource.setTargetDataSources(targetDataSources);
	}

}
package com.ltkj.template.dbconfig;

import java.util.Map;

import org.springframework.beans.PropertyValues;
import org.springframework.core.convert.ConversionService;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private ConversionService conversionService;
	private PropertyValues dataSourcePropertyValues;

	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	public ConversionService getConversionService() {
		return this.conversionService;
	}

	public void setDataSourcePropertyValues(PropertyValues dataSourcePropertyValues) {
		this.dataSourcePropertyValues = dataSourcePropertyValues;
	}

	public PropertyValues getDataSourcePropertyValues() {
		return this.dataSourcePropertyValues;
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceConnection();
	}

	@Override
	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}
}
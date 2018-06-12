package com.ltkj.template.dbconfig;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DynamicDataSourceContextHolder {
	@PostConstruct
	public void init() {
		contextHolder.set("dataSource");
	}

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static List<String> dataSourceConnections = new ArrayList<String>();

	public static void setDataSourceConnection(String dataSourceConnection) {
		contextHolder.set(dataSourceConnection);
	}

	public static String getDataSourceConnection() {
		return contextHolder.get();
	}

	public static void clearDataSourceConnection() {
		contextHolder.remove();
		contextHolder.set("dataSource");
	}

	public static boolean containsDataSource(String dataSourceConnection) {
		return dataSourceConnections.contains(dataSourceConnection);
	}
}
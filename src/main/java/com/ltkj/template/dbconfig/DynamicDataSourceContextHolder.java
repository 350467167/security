package com.ltkj.template.dbconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class DynamicDataSourceContextHolder {
	@PostConstruct
	public void init() {
		contextHolder.set("dataSource");
		autoCommit.set(true);
	}

	public static List<String> dataSourceConnections = new ArrayList<String>();

	private static final ThreadLocal<Boolean> autoCommit = new ThreadLocal<Boolean>();
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	private static final ThreadLocal<DynamicDataSourceTransaction> dynamicDataSourceTransaction = new ThreadLocal<DynamicDataSourceTransaction>();

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

	public static void setDynamicDataSourceTransaction(DynamicDataSourceTransaction transaction) {
		dynamicDataSourceTransaction.set(transaction);
	}

	public static DynamicDataSourceTransaction getDynamicDataSourceTransaction() {
		return dynamicDataSourceTransaction.get();
	}

	public static void cleanDynamicDataSourceTransaction() {
		dynamicDataSourceTransaction.remove();
	}

	public static void stopAutoCommit() {
		autoCommit.set(false);
	}

	public static Boolean getAutoCommit() {
		return autoCommit.get();
	}

	public static void cleanAutoCommit() {
		autoCommit.set(true);
	}

	public static void commit() {
		DynamicDataSourceTransaction transaction = getDynamicDataSourceTransaction();
		try {
			transaction.commitAll();
			transaction.closeAll();
			cleanDynamicDataSourceTransaction();
			cleanAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		DynamicDataSourceTransaction transaction = getDynamicDataSourceTransaction();
		try {
			transaction.rollbackAll();
			transaction.closeAll();
			cleanDynamicDataSourceTransaction();
			cleanAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
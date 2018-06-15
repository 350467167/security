package com.ltkj.template.dbconfig;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DynamicDataSourceContextHolder {
	public static List<String> dataSourceConnections = new ArrayList<String>();

	private static ThreadLocal<Boolean> autoCommit = new InheritableThreadLocal<Boolean>();
	private static ThreadLocal<String> contextHolder = new InheritableThreadLocal<String>();
	private static ThreadLocal<DynamicDataSourceTransaction> dynamicDataSourceTransaction = new InheritableThreadLocal<>();

	public static void setDataSourceConnection(String dataSourceConnection) {
		contextHolder.set(dataSourceConnection);
	}

	public static String getDataSourceConnection() {
		if (contextHolder.get() == null) {
			System.err.println("xxx");
			contextHolder.set("dataSource");
		}
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
		Thread t = Thread.currentThread();
		System.err.println(t);
		autoCommit.set(false);
	}

	public static Boolean getAutoCommit() {
		Thread t = Thread.currentThread();
		System.err.println(t);
		if (autoCommit.get() == null) {
			autoCommit.set(true);
		}
		return autoCommit.get();
	}

	public static void cleanAutoCommit() {
		autoCommit.set(true);
	}

	public static void commit() {
		System.err.println("commit" + getAutoCommit());
		DynamicDataSourceTransaction transaction = getDynamicDataSourceTransaction();
		try {
			transaction.commitAll();
			transaction.closeAll();
			cleanDynamicDataSourceTransaction();
			cleanAutoCommit();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void rollback() {
		System.err.println("rollback" + getAutoCommit());
		DynamicDataSourceTransaction transaction = getDynamicDataSourceTransaction();
		try {
			transaction.rollbackAll();
			transaction.closeAll();
			cleanDynamicDataSourceTransaction();
			cleanAutoCommit();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
package org.mybatis.spring.transaction;

import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;

import com.ltkj.template.dbconfig.DynamicDataSourceContextHolder;
import com.ltkj.template.dbconfig.DynamicDataSourceTransaction;

public class SpringManagedTransactionFactory implements TransactionFactory {
	@Override
	public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
		System.err.println(DynamicDataSourceContextHolder.getAutoCommit());
		System.err.println(DynamicDataSourceContextHolder.getDataSourceConnection());
		if (DynamicDataSourceContextHolder.getAutoCommit()) {
			return new SpringManagedTransaction(dataSource);
		} else {
			DynamicDataSourceTransaction transaction = DynamicDataSourceContextHolder.getDynamicDataSourceTransaction();
			return transaction != null ? transaction : new DynamicDataSourceTransaction(dataSource);
		}
	}

	@Override
	public Transaction newTransaction(Connection conn) {
		throw new UnsupportedOperationException("New Spring transactions require a DataSource");
	}

	@Override
	public void setProperties(Properties props) {
		// not needed in this version
	}

}

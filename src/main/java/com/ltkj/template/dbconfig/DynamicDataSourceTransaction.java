package com.ltkj.template.dbconfig;

import static org.apache.commons.lang3.Validate.notNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.sql.DataSource;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class DynamicDataSourceTransaction implements Transaction {
	private final DataSource dataSource;

	private Connection mainConnection;

	private String mainDatabaseIdentification;

	private ConcurrentMap<String, Connection> otherConnectionMap;

	private boolean autoCommit;

	public DynamicDataSourceTransaction(DataSource dataSource) {
		notNull(dataSource, "No DataSource specified");
		this.dataSource = dataSource;
		otherConnectionMap = new ConcurrentHashMap<>();
		mainDatabaseIdentification = DynamicDataSourceContextHolder.getDataSourceConnection();
	}

	@Override
	public Connection getConnection() throws SQLException {
		DynamicDataSourceContextHolder.setDynamicDataSourceTransaction(this);
		String connection = DynamicDataSourceContextHolder.getDataSourceConnection();
		if (mainDatabaseIdentification.equals(connection)) {
			if (mainConnection == null) {
				openMainConnection();
			}
			mainConnection.setAutoCommit(this.autoCommit);
			return mainConnection;
		} else {
			if (!otherConnectionMap.containsKey(connection)) {
				try {
					Connection conn = dataSource.getConnection();
					conn.setAutoCommit(this.autoCommit);
					otherConnectionMap.put(connection, conn);
				} catch (SQLException ex) {
					throw new CannotGetJdbcConnectionException("Could not get JDBC Connection", ex);
				}
			}
			return otherConnectionMap.get(connection);
		}

	}

	private void openMainConnection() throws SQLException {
		this.mainConnection = DataSourceUtils.getConnection(this.dataSource);
		this.autoCommit = DynamicDataSourceContextHolder.getAutoCommit();
	}

	@Override
	public void commit() throws SQLException {
	}

	@Override
	public void rollback() throws SQLException {
	}

	@Override
	public void close() throws SQLException {
	}

	public void commitAll() throws SQLException {
		this.mainConnection.commit();
		for (Connection connection : otherConnectionMap.values()) {
			connection.commit();
		}
	}

	public void rollbackAll() throws SQLException {
		this.mainConnection.rollback();
		for (Connection connection : otherConnectionMap.values()) {
			connection.rollback();
		}
	}

	public void closeAll() throws SQLException {
		DataSourceUtils.releaseConnection(this.mainConnection, this.dataSource);
		for (Connection connection : otherConnectionMap.values()) {
			DataSourceUtils.releaseConnection(connection, this.dataSource);
		}
	}

	@Override
	public Integer getTimeout() throws SQLException {
		return null;
	}
}
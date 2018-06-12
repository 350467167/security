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

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

public class MultiDataSourceTransaction implements Transaction {
	private static final Log LOGGER = LogFactory.getLog(MultiDataSourceTransaction.class);

	private final DataSource dataSource;

	private Connection mainConnection;

	private String mainDatabaseIdentification;

	private ConcurrentMap<String, Connection> otherConnectionMap;

	private boolean isConnectionTransactional;

	private boolean autoCommit;

	public MultiDataSourceTransaction(DataSource dataSource) {
		notNull(dataSource, "No DataSource specified");
		this.dataSource = dataSource;
		otherConnectionMap = new ConcurrentHashMap<>();
		mainDatabaseIdentification = DynamicDataSourceContextHolder.getDataSourceConnection();
	}

	@Override
	public Connection getConnection() throws SQLException {
		String connection = DynamicDataSourceContextHolder.getDataSourceConnection();
		if (mainDatabaseIdentification.equals(connection)) {
			if (mainConnection != null)
				return mainConnection;
			else {
				openMainConnection();
				return mainConnection;
			}
		} else {
			if (!otherConnectionMap.containsKey(connection)) {
				try {
					Connection conn = dataSource.getConnection();
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
		this.autoCommit = this.mainConnection.getAutoCommit();
		this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(this.mainConnection,
				this.dataSource);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("JDBC Connection [" + this.mainConnection + "] will"
					+ (this.isConnectionTransactional ? " " : " not ") + "be managed by Spring");
		}
	}

	@Override
	public void commit() throws SQLException {
		if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Committing JDBC Connection [" + this.mainConnection + "]");
			}
			this.mainConnection.commit();
			for (Connection connection : otherConnectionMap.values()) {
				connection.commit();
			}
		}
	}

	@Override
	public void rollback() throws SQLException {
		if (this.mainConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Rolling back JDBC Connection [" + this.mainConnection + "]");
			}
			this.mainConnection.rollback();
			for (Connection connection : otherConnectionMap.values()) {
				connection.rollback();
			}
		}
	}

	@Override
	public void close() throws SQLException {
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
/**
 *    Copyright 2010-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.spring.transaction;

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
import com.ltkj.template.dbconfig.DynamicDataSourceContextHolder;

public class SpringManagedTransaction implements Transaction {
	private static final Log LOGGER = LogFactory.getLog(SpringManagedTransaction.class);

	private final DataSource dataSource;

	private Connection mainConnection;

	private String mainDatabaseIdentification;

	private ConcurrentMap<String, Connection> otherConnectionMap;

	private boolean isConnectionTransactional;

	private boolean autoCommit;

	public SpringManagedTransaction(DataSource dataSource) {
		System.err.println("************SpringManagedTransaction***************");
		notNull(dataSource, "No DataSource specified");
		this.dataSource = dataSource;
		otherConnectionMap = new ConcurrentHashMap<>();
		mainDatabaseIdentification = DynamicDataSourceContextHolder.getDataSourceConnection();
	}

	@Override
	public Connection getConnection() throws SQLException {
		System.err.println("************getConnection***************");
		String connection = DynamicDataSourceContextHolder.getDataSourceConnection();
		if (mainDatabaseIdentification.equals(connection)) {
			if (mainConnection != null) {
				return mainConnection;
			} else {
				openMainConnection();
				return mainConnection;
			}
		} else {
			if (!otherConnectionMap.containsKey(connection)) {
				try {
					// dataSource.get
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
		System.err.println("************openMainConnection***************");
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
		System.err.println("************commit***************");
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
		System.err.println("************rollback***************");
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
		System.err.println("************close***************");
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
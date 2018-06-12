//package com.ltkj.template.dbconfig;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Configuration
//@EnableTransactionManagement
//@Slf4j
//public class MyDataSourceTransactionManagerAutoConfiguration extends DataSourceTransactionManagerAutoConfiguration
//		implements ApplicationContextAware {
//	private ConfigurableApplicationContext applicationContext = null;
//
//	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
//	}
//
//	public ConfigurableApplicationContext getApplicationContext() {
//		return applicationContext;
//	}
//
//	/**
//	 * 自定义事务 MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.
//	 * SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，
//	 * 否则事务管理会不起作用。
//	 * 
//	 * @return
//	 */
//	// @Bean(name = "transactionManager")
//	// public DataSourceTransactionManager transactionManagers() {
//	// log.info("-------------------- transactionManager init
//	// ---------------------");
//	//
//	// return new
//	// DataSourceTransactionManager(SpringContextHolder.getBean("roundRobinDataSouceProxy"));
//	// }
//}
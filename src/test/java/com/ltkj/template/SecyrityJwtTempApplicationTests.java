package com.ltkj.template;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.ltkj.template.dbconfig.DynamicDataSourceRegister;
import com.ltkj.template.dbconfig.DynamicLoadBean;
import com.ltkj.template.dbconfig.MultiDataSourceTransactionFactory;
import com.ltkj.template.model.User;
import com.ltkj.template.service.transactionalService.UserServiceTran;

@EnableTransactionManagement
@Import({ DynamicDataSourceRegister.class, MultiDataSourceTransactionFactory.class })
@MapperScan("com.ltkj.template.mapper")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecyrityJwtTempApplicationTests {
	@Autowired
	DynamicLoadBean dynamicBeanLoad;
	@Autowired
	UserServiceTran userServiceTran;

	@Test
	public void run() throws Exception {
		// call();
		insert();
	}

	private void insert() {
		String ds = "{\"driverClassName\":\"com.microsoft.sqlserver.jdbc.SQLServerDriver\", \"type\": \"com.alibaba.druid.pool.DruidDataSource\","
				+ "\"url\":\"jdbc:sqlserver://127.0.0.1:1433;DatabaseName=user_db2\",\"username\":\"root2\", \"password\":\"root2\"}";

		User user = new User();
		user.setEmail("123xxx");
		user.setId("123456789");
		user.setPassword("111");
		user.setUsername("cccc");

		userServiceTran.insert(ds, user);

	}

	// @Transactional
	// private void call() {
	// String s =
	// "{\"driverClassName\":\"com.microsoft.sqlserver.jdbc.SQLServerDriver\",
	// \"type\": \"com.alibaba.druid.pool.DruidDataSource\","
	// +
	// "\"url\":\"jdbc:sqlserver://127.0.0.1:1433;DatabaseName=user_db\",\"username\":\"root\",
	// \"password\":\"root\"}";
	//
	// System.err.println("********************");
	//
	// List<User> l = userService.findAllByDynamicDB(s);
	// l.forEach(action -> {
	// System.out.println("*** : " + action);
	// });
	//
	// System.err.println("********************");
	//
	// List<User> l2 = userService.findAll();
	// l2.forEach(action -> {
	// System.out.println("*** : " + action);
	// });
	//
	// System.err.println("********************");
	//
	// List<User> l3 = userService.findAllByDynamicDB(s);
	// l3.forEach(action -> {
	// System.out.println("*** : " + action);
	// });
	//
	// System.err.println("********************");
	// }
}

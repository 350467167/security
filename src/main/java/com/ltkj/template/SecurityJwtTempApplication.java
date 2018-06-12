package com.ltkj.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ltkj.template.dbconfig.DynamicDataSourceRegister;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.ltkj.template.mapper")
@Import({ DynamicDataSourceRegister.class })
public class SecurityJwtTempApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtTempApplication.class, args);
	}
}

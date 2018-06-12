package com.ltkj.template.dbconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(-10)
@Component
public class DynamicDataSourceAspect {
	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface TargetDataSource {
	}

	@Autowired
	DynamicLoadBean dynamicLoadBean;

	@Pointcut("@annotation(com.ltkj.template.dbconfig.DynamicDataSourceAspect.TargetDataSource)")
	public void controllerAspect() {
	}

	@Before("controllerAspect()")
	public void changeDataSource(JoinPoint point) {
		String dataSourceConnection = (String) point.getArgs()[0];

		if (!DynamicDataSourceContextHolder.containsDataSource(dataSourceConnection)) {
			System.err.println("******registBean*******");
			dynamicLoadBean.registBean(dataSourceConnection);
		}

		DynamicDataSourceContextHolder.setDataSourceConnection(dataSourceConnection);
	}

	@After("controllerAspect()")
	public void restoreDataSource(JoinPoint point) {
		DynamicDataSourceContextHolder.clearDataSourceConnection();
	}

	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void throwException(JoinPoint point, Throwable e) {
		System.out.println("目标方法中抛出的异常:" + e);
		System.out.println("模拟抛出异常后的增强处理...");
	}
}
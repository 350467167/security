package com.ltkj.template.dbconfig;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {
	@Autowired
	DynamicLoadBean dynamicLoadBean;

	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface TargetDataSource {
	}

	@Target({ ElementType.METHOD, ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface TranExtend {
	}

	@Pointcut("@annotation(com.ltkj.template.dbconfig.DynamicDataSourceAspect.TargetDataSource)")
	public void controllerAspect() {
	}

	@Pointcut("@annotation(com.ltkj.template.dbconfig.DynamicDataSourceAspect.TranExtend)")
	public void tansationalExtendAspect() {
	}

	@Before("controllerAspect()")
	public void changeDataSource(JoinPoint point) {
		String dataSourceConnection = (String) point.getArgs()[0];
		if (!DynamicDataSourceContextHolder.containsDataSource(dataSourceConnection)) {
			dynamicLoadBean.registBean(dataSourceConnection);
		}

		DynamicDataSourceContextHolder.setDataSourceConnection(dataSourceConnection);
	}

	@After("controllerAspect()")
	public void restoreDataSource(JoinPoint point) {
		DynamicDataSourceContextHolder.clearDataSourceConnection();
	}

	@Before("tansationalExtendAspect()")
	public void beforeTansationalExtendAspect(JoinPoint point) {
		// DynamicDataSourceContextHolder.stopAutoCommit();
	}

	@AfterReturning("tansationalExtendAspect()")
	public void afterTansationalExtendAspect(JoinPoint point) {
		// DynamicDataSourceContextHolder.clearDataSourceConnection();
		// DynamicDataSourceContextHolder.commit();
	}

	@AfterThrowing(pointcut = "tansationalExtendAspect()", throwing = "e")
	public void throwException(JoinPoint point, Throwable e) {
		// DynamicDataSourceContextHolder.rollback();
		System.err.println("目标方法中抛出的异常:" + e);
		System.err.println("模拟抛出异常后的增强处理...");
	}
}
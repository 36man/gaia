package org.bravo.gaia.mybatis.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.PersistenceExceptionTranslator;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * @author lijian
 */
@Configuration(proxyBeanMethods = false)
public class MybatisConfig {

    public static final String DEFAULT_MAPPER_SCAN_PACKAGE = "**.dao,**.repository";

	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(Set<InnerInterceptor> interceptors){
		List<InnerInterceptor> defaultInterceptor = new ArrayList<>();
		defaultInterceptor.add(new OptimisticLockerInnerInterceptor());
		defaultInterceptor.add(new PaginationInnerInterceptor());

		Set<InnerInterceptor> innerInterceptors = Optional.ofNullable(interceptors).orElseGet(HashSet::new);
		innerInterceptors.addAll(defaultInterceptor);

		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		mybatisPlusInterceptor.setInterceptors(innerInterceptors.stream().collect(Collectors.toList()));

		return mybatisPlusInterceptor;
	}

	@Bean
	public MapperScannerConfigurer scannerConfigurer(Environment environment) {
		String mybatisScanPackage = environment.getProperty("mybatis.mapper.scan");

		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

		mybatisScanPackage = StringUtils.isBlank(mybatisScanPackage) ? DEFAULT_MAPPER_SCAN_PACKAGE
				: mybatisScanPackage;

		mapperScannerConfigurer.setBasePackage(mybatisScanPackage);

		return mapperScannerConfigurer;
	}

	@Bean
	public PersistenceExceptionTranslator persistenceExceptionTranslator(DataSource dataSource) {

		return new MyBatisExceptionTranslator(dataSource, false);
	}
	
}

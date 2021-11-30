package org.bravo.gaia.mybatis.config;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author lijian
 */
@Configuration
public class MybatisConfig {
	
    public static final String MYBATIS_SCAN_PACKAGE  = "**.dao";

    @Value("${mybatis.general.mapper.scan}")
    private String             mybatisScanPackage;

	/**
	 * 通用mapper配置
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer(){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		Config config = new Config();
		mapperScannerConfigurer.getMapperHelper().setConfig(config);
        mybatisScanPackage = StringUtils.isEmpty(mybatisScanPackage) ? MYBATIS_SCAN_PACKAGE : mybatisScanPackage;
		mapperScannerConfigurer.setBasePackage(mybatisScanPackage);
		Properties prop = new Properties();
		mapperScannerConfigurer.setProperties(prop);
		return  mapperScannerConfigurer;
	}

	@Bean
	public PersistenceExceptionTranslator persistenceExceptionTranslator(DataSource dataSource) {

		return new MyBatisExceptionTranslator(dataSource, false);
	}
	
}

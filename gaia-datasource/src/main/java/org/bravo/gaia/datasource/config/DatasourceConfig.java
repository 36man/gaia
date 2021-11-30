package org.bravo.gaia.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

/**
 * @author lijian
 * @version $Id: DatasourceConfig.java, v 0.1 2018年04月11日 17:31 lijian Exp $
 */
@Configuration
public class DatasourceConfig {

    private final static Logger LOG = LoggerFactory.getLogger(DatasourceConfig.class);

    @Value("${default.datasource.user:platform}")
    private String defaultDatasourceUser;
    @Value("${default.datasource.password:123}")
    private String defaultDatasourcePassword;

    @Bean
    @ConditionalOnProperty(value = "datasource.test", havingValue = "true")
    public DataSource dataSource() throws SQLException {
        LOG.info("构建H2测试数据库");

        File dir = new File(System.getProperty("user.dir"),"domain");
        dir.mkdirs();
        DruidDataSource dataSource = new DruidDataSource();
        String url ="jdbc:h2:" +  dir + File.separator + "platform;AUTO_SERVER=TRUE";
        dataSource.setUrl(url);
        dataSource.setUsername(defaultDatasourceUser);
        dataSource.setPassword(defaultDatasourcePassword);
        return dataSource;
    }

}
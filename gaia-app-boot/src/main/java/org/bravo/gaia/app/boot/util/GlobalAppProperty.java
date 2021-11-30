package org.bravo.gaia.app.boot.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lijian
 * @since 2021/11/30
 */
public class GlobalAppProperty {

    /** 加载所有jar包类路径下面的config/app.properties */
    private static final String                  APP_PROPERTIES            = "config/app.properties";
    private static final ResourcePatternResolver RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();
    private static final List<Properties> PROPERTIES_LIST           = new ArrayList<>();

    static{
        try {
            Resource[] xmlResources = RESOURCE_PATTERN_RESOLVER.getResources("classpath*:" + APP_PROPERTIES);
            for (Resource resource : xmlResources) {
                Properties propertiesFile = new Properties();
                InputStream is = resource.getInputStream();
                propertiesFile.load(is);
                is.close();
                PROPERTIES_LIST.add(propertiesFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Properties> findAllAppProperties(){
        return PROPERTIES_LIST;
    }


    /**
     * 随机拿到第一个，该方法一般很少使用，除非确定当前的key值是全系统唯一的
     */
    public static String getAppProperty(String key){
        for (Properties properties : PROPERTIES_LIST) {
            if(properties.getProperty(key) != null){
                return properties.getProperty(key);
            }
        }
        return null;
    }

}

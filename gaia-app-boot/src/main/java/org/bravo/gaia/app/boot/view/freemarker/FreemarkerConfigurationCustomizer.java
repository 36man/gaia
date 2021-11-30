package org.bravo.gaia.app.boot.view.freemarker;

import org.apache.commons.lang3.ArrayUtils;
import org.bravo.gaia.app.boot.util.GlobalAppProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 *
 *改造spring-boot对于freemarker的集成，原始集成freemarker的模板只能扫描当前工程，
 *通过重写方法重新加载所有类路径下的模板，使得整个框架能够扫描所有工程
 *@author lijian
 */
@Configuration
@AutoConfigureBefore(FreeMarkerAutoConfiguration.class)
public class FreemarkerConfigurationCustomizer implements WebMvcConfigurer {
	private static Logger LOG = LoggerFactory.getLogger(FreemarkerConfigurationCustomizer.class);

	/** app properties当中的ftl键 */
	private static final String FTL = "ftl";
	ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	
	@Autowired(required = false)
	public void setProperties(FreeMarkerProperties properties) {
		try {
			if (properties == null) {
				LOG.warn("freemarker properties注入失败，请检查");
				return;
			}

			//加载各jar包下的platform/template的html页面
            populateJarTemplates(properties);

			//加载类路径下的ftl宏
            populateFtlMicro(properties);
		} catch (IOException e) {
			LOG.error("系统加载模板资源信息失败");
			throw new RuntimeException(e);
		}
	}

    /**
     * 默认情况下，freemarker是不会加载其他jar包内的templates的，也就是加载路径为classpath:,
     * 为了使得freemarker能够加载到类路径下所有jar内的template，所以需要将加载路径扩展为classpath*:
     */
    private void populateJarTemplates(FreeMarkerProperties properties) throws IOException {
        Resource[] resources = resourcePatternResolver.getResources("classpath*:/platform/templates/");
        String[] templateLoaderPaths = new String[resources.length];
        for (int i = 0; i < resources.length; i++) {
            String templateLoaderPath = resources[i].getURI().toString();
            templateLoaderPaths[i] = templateLoaderPath;
        }
        String[] oldTemplateLoaderPaths = properties.getTemplateLoaderPath();
        properties.setTemplateLoaderPath(ArrayUtils.addAll(templateLoaderPaths, oldTemplateLoaderPaths));
    }

    /**
     * 系统扩展点，每一个jar包内的类路径下config/app.properties中定义了一个ftl属性，
     * 用于不同的jar定义属于自己的freemarke宏
     *
     */
    private void populateFtlMicro(FreeMarkerProperties properties) {
        String springFtl = "\"/spring.ftl\" as spring";
        String allFtl = findAllFtl();
        String ftls = allFtl == null ? springFtl : springFtl + "," + allFtl;
        properties.getSettings().put("auto_import", ftls);
    }

    /**
     * 加载系统classpath下config/app.properties.并读取其中ftl配置的ftl宏页面
     * @return ftl宏页面，多个ftl以逗号相隔。例如：no1.ftl,no2.ftl
     */
	private String findAllFtl(){
		List<Properties> allProperties = GlobalAppProperty.findAllAppProperties();
		StringBuffer sb = new StringBuffer();
		for (Properties properties : allProperties) {
			String ftlValue = properties.getProperty(FTL);
			if(ftlValue != null){
				sb.append(ftlValue + ",");
			}
		}
		if(sb.length() == 0){
			return null;
		}else{
			return sb.substring(0, sb.length() - 1);
		}
	}

    /**
     * 每次请求都解析参数locale，用于国际化
     */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LocaleChangeInterceptor());
	}
	
	
}

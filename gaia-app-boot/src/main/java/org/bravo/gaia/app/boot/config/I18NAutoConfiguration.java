package org.bravo.gaia.app.boot.config;

import org.bravo.gaia.app.boot.i18n.MessageUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 国际化配置
 * @author lijian
 * @since 2021/11/29
 */

@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class I18NAutoConfiguration {
    @Bean
    public MessageSource messageSource() {
        return MessageUtil.getMessageSource();
    }

    @Bean(name = DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }
}

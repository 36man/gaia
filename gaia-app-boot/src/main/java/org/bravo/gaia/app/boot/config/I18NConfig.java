package org.bravo.gaia.app.boot.config;

import org.bravo.gaia.app.boot.i18n.MessageUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 国际化配置
 * @author lijian
 * @since 2021/11/29
 */
@Configuration
public class I18NConfig {
    @Bean
    public MessageSource messageSource() {
        return MessageUtil.getMessageSource();
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new SessionLocaleResolver();
    }
}

package org.bravo.gaia.app.boot.config;

import org.bravo.gaia.app.boot.interceptor.ExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * global exception handler config
 * @author lijian
 * @version $Id: ExceptionHandlerConfig.java, v 0.1 2018年04月11日 9:56 lijian Exp $
 */
@Configuration
public class ExceptionHandlerConfig {

    @Bean
    public HandlerExceptionResolver exceptionHandler() {
        return new ExceptionHandler();
    }

}
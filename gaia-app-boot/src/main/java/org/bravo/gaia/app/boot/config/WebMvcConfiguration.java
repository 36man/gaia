package org.bravo.gaia.app.boot.config;

import java.util.List;

import org.bravo.gaia.app.boot.interceptor.ExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author hejianbing
 * @version @Id: WebMvcConfiguration.java, v 0.1 2023年01月08日 17:42 hejianbing Exp $
 */
@Configuration(proxyBeanMethods = false)
public class WebMvcConfiguration implements WebMvcConfigurer {

    private ObjectProvider<ObjectMapper> objectMapperProvider;

    public WebMvcConfiguration(ObjectProvider<ObjectMapper> objectMapperProvider) {
        this.objectMapperProvider = objectMapperProvider;
    }

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        ObjectMapper objectMapper = objectMapperProvider.getIfAvailable();
        resolvers.add(new ExceptionHandler(objectMapper));

    }
}
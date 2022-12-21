package org.bravo.gaia.id.config;

import org.bravo.gaia.id.generator.impl.VestaIdGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jojocodex
 * @version @Id: IdGeneratorAutoConfiguration.java, v 0.1 2022年12月21日 09:43 jojocodex Exp $
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(IdProperties.class)
public class IdGeneratorConfiguration {


    @Bean
    @ConditionalOnProperty(prefix = "gaia.id", name = "type", havingValue = "vesta", matchIfMissing = true)
    public VestaIdGenerator vestaIdGenerator(IdProperties idProperties) {
        return new VestaIdGenerator(idProperties.getVesta());
    }
}
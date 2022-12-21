package org.bravo.gaia.id.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomUtils;
import org.bravo.gaia.id.enums.IdType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author jojocodex
 * @version @Id: IdProperties.java, v 0.1 2022年12月21日 15:04 jojocodex Exp $
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gaia.id")
public class IdProperties {

    @NestedConfigurationProperty
    private Vesta vesta = new Vesta();

    private IdType          type  = IdType.VESTA;

    @Getter
    @Setter
    public static class Vesta {
        public long type      = 1;
        public long genMethod = 0;
        public long machine = RandomUtils.nextLong(1L, 1024L);
    }
}
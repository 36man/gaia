/**
 * bravo.org
 * Copyright (c) 2018-2019 ALL Rights Reserved
 */
package org.bravo.gaia.id.config;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomUtils;
import org.bravo.gaia.id.enums.IdType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author hejianbing
 * @version @Id: IdProperties.java, v 0.1 2021年04月12日 13:19 hejianbing Exp $
 */
@Configuration
@ConfigurationProperties(prefix = "gaia.id")
@Setter
@Getter
public class IdProperties {

    @NestedConfigurationProperty
    private Vesta vesta = new Vesta();

    private IdType          type  = IdType.VESTA;

    @Getter
    @Setter
    public static class Vesta {
        /** ID类型，1表示最小粒度型  0表示最大峰值 */
        public long type      = 1;

        /** 生成方式，0表示 嵌入发布模式 1表示 中心服务器发布模式 2表示 REST发 布模式 */
        public long genMethod = 0;

        public long machine = RandomUtils.nextLong(1L, 1024L);
    }
}
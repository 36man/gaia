package org.bravo.gaia.app.boot.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * string trim controller处理
 * 处理http request body里面的入参
 * @author lijian
 * @version @Id: SpringJsonStringTrimExtend.java, v 0.1 2019年07月30日 19:07 lijian Exp $
 */
@Configuration
public class SpringJsonStringTrimExtend {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            //为String 类型自定义反序列化操作
            jacksonObjectMapperBuilder
                    .deserializerByType(String.class, new StdScalarDeserializer<String>(String.class) {
                        @Override
                        public String deserialize(JsonParser jsonParser, DeserializationContext ctx)
                                throws IOException {
                            // 去除前后空格
                            return StringUtils.trim(jsonParser.getValueAsString());
                        }
                    });
        };
    }

}

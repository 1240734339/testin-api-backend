package cn.testin.config;

import cn.testin.commons.utils.JacksonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * @author WangG
 * @title: jackSonConfig
 * @description: jackSon全局配置
 */
@Configuration
public class jackSonConfig {
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory()
                .withSerializerModifier(new JacksonUtil.MyBeanSerializerModifier()));
        SerializerProvider serializerProvider = objectMapper.getSerializerProvider();
        serializerProvider.setNullValueSerializer(new JacksonUtil.CustomizeNullJsonSerializer
                .NullObjectJsonSerializer());
        return objectMapper;
    }
}
//
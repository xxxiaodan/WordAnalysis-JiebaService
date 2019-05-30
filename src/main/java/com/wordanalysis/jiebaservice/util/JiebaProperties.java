package com.wordanalysis.jiebaservice.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author XiaoDan 2019-5
 */
@Configuration
@PropertySource("classpath:jieba.properties")
@Data
@Component
@ConfigurationProperties(ignoreUnknownFields = true)
public class JiebaProperties {
    private String MyDictionaryPath;

    public String getMyDictionaryPath() {
        return MyDictionaryPath;
    }
}

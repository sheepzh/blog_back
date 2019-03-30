package zhy.blog.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zhy.blog.util.LevelDbConfig;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomerWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        messageConverter.setSupportedMediaTypes(fastMediaTypes);
//        fastJsonConfig.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
        messageConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(messageConverter);
    }

    @Bean
    public LevelDbConfig getLevelDbConfig() {
        return new LevelDbConfig().setCharset("utf-8").setDbRoot("/home/zhyyy/blogdb");
    }
}

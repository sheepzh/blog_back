package zhy.blog.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import zhy.blog.web.UrlInterceptor;

@Component
public class BlogWebConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UrlInterceptor());
		super.addInterceptors(registry);
	}
}

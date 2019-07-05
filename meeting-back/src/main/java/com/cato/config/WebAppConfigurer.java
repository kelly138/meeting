package com.cato.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Company: Cato Studio
 * @Author: WangShang
 * @Time: 2019/4/27
 * @Description:
 */
@Configuration
public class WebAppConfigurer {

	/**
	 * 跨域过滤器
	 *
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}

	/**
	 * 跨域全局配置
	 *
	 * @return
	 */
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		// 允许所有的请求域名访问我们的跨域资源
		corsConfiguration.addAllowedOrigin("*");
		// 允许所有的请求header访问，可以自定义设置任意请求头信息
		corsConfiguration.addAllowedHeader("*");
		// 允许通过所有的请求方式访问该跨域资源服务器，如：POST
		corsConfiguration.addAllowedMethod("*");
		// 要求跨域请求提供凭据信息
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}

}
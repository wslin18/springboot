package com.example.springboot.config;


import com.alibaba.fastjson2.support.config.FastJsonConfig;

import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;
import com.example.springboot.core.Result;
import com.example.springboot.core.ResultCode;
import com.example.springboot.core.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson2.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * @author wang
 * Spring MVC 配置
 */
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {

	@Value("${spring.profiles.active}")
	private String env;


	@Bean
	public FastJsonConfig fastJsonConfig() {
		//1.自定义配置...
		FastJsonConfig config = new FastJsonConfig();
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		//2.1配置序列化的行为
		//JSONWriter.Feature.PrettyFormat:格式化输出
		config.setWriterFeatures(JSONWriter.Feature.PrettyFormat);
		//2.2配置反序列化的行为
		config.setReaderFeatures(JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean);
		return config;
	}
	/**
	 *  使用 FastJsonHttpMessageConverter 来替换 Spring MVC 默认的 HttpMessageConverter
	 *  以提高 @RestController @ResponseBody @RequestBody 注解的 JSON序列化和反序列化速度。
	 * */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//1.转换器
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setDefaultCharset(StandardCharsets.UTF_8);
		converter.setFastJsonConfig(fastJsonConfig());
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		converters.add(0, converter);
	}




	/**
	 * 统一异常处理
	 * @param exceptionResolvers initially an empty list
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(new HandlerExceptionResolver() {
			@Override
			public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
												 Object handler, Exception e) {
				Result result = new Result();
				// 业务失败的异常，如“账号或密码错误”
				if (e instanceof ServiceException) {
					result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
					log.info(e.getMessage());
				} else if (e instanceof NoHandlerFoundException) {
					result.setCode(ResultCode.NOT_FOUND).setMessage("接口 [" + request.getRequestURI() + "] 不存在");
				} else if (e instanceof ServletException) {
					result.setCode(ResultCode.FAIL).setMessage(e.getMessage());
				} else {
					result.setCode(ResultCode.INTERNAL_SERVER_ERROR)
							.setMessage("接口 [" + request.getRequestURI() + "] 内部错误");
					String message;
					if (handler instanceof HandlerMethod) {
						HandlerMethod handlerMethod = (HandlerMethod) handler;
						message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s", request.getRequestURI(),
								handlerMethod.getBean().getClass().getName(), handlerMethod.getMethod().getName(),
								e.getMessage());
					} else {
						message = e.getMessage();
					}
					log.error(message, e);
				}
				responseResult(response, result);
				return new ModelAndView();
			}

		});
	}

	/**
	 * 处理返回值跨域问题
	 * @param response
	 * @param result
	 */
	private void responseResult(HttpServletResponse response, Result result) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/json;charset=UTF-8");
		response.setStatus(200);
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

	
}

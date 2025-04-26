package com.jhonn.santt4na_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
		// Via Extension. _.xml _.JSON
		
		// Via Query Param - ?mediaType=xml
		
		//configurer.favorParameter(true)
		//	.parameterName("mediaType")
		//	.ignoreAcceptHeader(true)
		//	.useRegisteredExtensionsOnly(false)
		//	.defaultContentType(MediaType.APPLICATION_JSON)
		//	.mediaType("json", MediaType.APPLICATION_JSON)
		//	.mediaType("xml", MediaType.APPLICATION_XML);
		
		// Via header param
		configurer.favorParameter(false)
			.ignoreAcceptHeader(false)
			.useRegisteredExtensionsOnly(false)
			.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON)
				.mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("yaml", MediaType.APPLICATION_YAML);
	}
	
	@Bean
	public MappingJackson2XmlHttpMessageConverter xmlHttpMessageConverter() {
		XmlMapper xmlMapper = XmlMapper.builder()
			.defaultUseWrapper(false)
			.build();
		
		MappingJackson2XmlHttpMessageConverter xmlConverter =
			new MappingJackson2XmlHttpMessageConverter(xmlMapper);
		
		xmlConverter.setSupportedMediaTypes(
			List.of(MediaType.APPLICATION_XML, MediaType.TEXT_XML)
		);
		
		return xmlConverter;
	}
}
package com.jhonn.santt4na_rest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${cors.originPatterns}") // Chamando o cors do application YML
	private String corsOriginPatterns = "";
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		var allowedOrigins = corsOriginPatterns.split(",");
		registry.addMapping("/**")
			.allowedOrigins(allowedOrigins)
			//.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
			.allowedMethods("*") // Aceitamos todos os metodos http
			.allowCredentials(true);
		
		/*Quando usamos o cors global devemos remover os Config cors dos controllers
		* pois o global gerencia tudo.
		*/
	
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		
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
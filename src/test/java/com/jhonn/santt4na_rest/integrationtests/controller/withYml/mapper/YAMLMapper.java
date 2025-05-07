package com.jhonn.santt4na_rest.integrationtests.controller.withYml.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.type.TypeFactory;

public class YAMLMapper implements ObjectMapper {
	
	private final com.fasterxml.jackson.databind.ObjectMapper mapper;
	protected TypeFactory typeFactory;
	
	public YAMLMapper() {
		mapper = new com.fasterxml.jackson.databind.ObjectMapper(new YAMLFactory())
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		typeFactory = TypeFactory.defaultInstance();
	}
	
	@Override
	public Object deserialize(ObjectMapperDeserializationContext context) {
		String content = context.getDataToDeserialize().asString();
		JavaType javaType = mapper.getTypeFactory().constructType(context.getType());
		
		try {
			return mapper.readValue(content, javaType);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error deserializing YAML content", e);
		}
	}
	
	
	@Override
	public Object serialize(ObjectMapperSerializationContext context) {
		try {
			return mapper.writeValueAsString(context.getObjectToSerialize());
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("Error serializing YAML content", e);
		}
	}
}

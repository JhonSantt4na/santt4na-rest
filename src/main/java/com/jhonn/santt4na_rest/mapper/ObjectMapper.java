package com.jhonn.santt4na_rest.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {
	
	private static final Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	public static <Entity, DTO> DTO parseObject(Entity entity, Class<DTO> dtoClass) {
		return mapper.map(entity, dtoClass);
	}
	
	public static <Entity, DTO> List<DTO> parseObjects(List<Entity> entities, Class<DTO> dtoClass) {
		List<DTO> dtos = new ArrayList<>();
		for (Entity entity : entities) {
			dtos.add(mapper.map(entity, dtoClass));
		}
		return dtos;
	}
}
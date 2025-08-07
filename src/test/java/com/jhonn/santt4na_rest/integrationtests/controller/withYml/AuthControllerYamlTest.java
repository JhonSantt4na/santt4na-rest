package com.jhonn.santt4na_rest.integrationtests.controller.withYml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jhonn.santt4na_rest.config.TestConfigs;
import com.jhonn.santt4na_rest.integrationtests.AbstractIntegrationTest;
import com.jhonn.santt4na_rest.integrationtests.controller.withYml.mapper.YAMLMapper;
import com.jhonn.santt4na_rest.integrationtests.dto.AccountCredentialsDTO;
import com.jhonn.santt4na_rest.integrationtests.dto.TokenDTO;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerYamlTest extends AbstractIntegrationTest {
	
	private static TokenDTO tokenDto;
	private static YAMLMapper objectMapper;
	
	@BeforeAll
	static void setUp() {
		objectMapper = new YAMLMapper();
		
		tokenDto = new TokenDTO();
	}
	
	@Test
	@Order(1)
	void signin() throws JsonProcessingException {
		AccountCredentialsDTO credentials =
			new AccountCredentialsDTO("leandro", "admin123");
		
		tokenDto = given()
			.config(
				RestAssuredConfig.config()
					.encoderConfig(
						EncoderConfig.encoderConfig().
							encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
			)
			.basePath("/auth/signin")
			.port(TestConfigs.SERVER_PORT)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.body(credentials, objectMapper)
			.when()
			.post()
			.then()
			.statusCode(200)
			.extract()
			.body()
			.as(TokenDTO.class, objectMapper);
		
		assertNotNull(tokenDto.getAccessToken());
		assertNotNull(tokenDto.getRefreshToken());
	}
	
	@Test
	@Order(2)
	void refreshToken() throws JsonProcessingException {
		tokenDto = given().config(
				RestAssuredConfig.config()
					.encoderConfig(
						EncoderConfig.encoderConfig().
							encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
			)
			.basePath("/auth/refresh")
			.port(TestConfigs.SERVER_PORT)
			.contentType(MediaType.APPLICATION_YAML_VALUE)
			.accept(MediaType.APPLICATION_YAML_VALUE)
			.pathParam("username", tokenDto.getUsername())
			.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenDto.getRefreshToken())
			.when()
			.put("{username}")
			.then()
			.statusCode(200)
			.extract()
			.body()
			.as(TokenDTO.class, objectMapper);
		
		assertNotNull(tokenDto.getAccessToken());
		assertNotNull(tokenDto.getRefreshToken());
	}
}
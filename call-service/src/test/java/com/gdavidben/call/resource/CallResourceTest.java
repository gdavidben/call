package com.gdavidben.call.resource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdavidben.call.api.CallProperties;
import com.gdavidben.call.payload.Call;
import com.gdavidben.call.payload.Statistic;
import com.gdavidben.call.payload.Type;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

@QuarkusTest
class CallResourceTest {

	private static final String PATH = CallProperties.APPLICATION_PATH + CallProperties.CALL_RESOURCE_PATH;
	private static final String PATH_ID = CallProperties.APPLICATION_PATH + CallProperties.CALL_RESOURCE_PATH + "/%s";
	private static final String PATH_STATISTICS = CallProperties.APPLICATION_PATH + CallProperties.CALL_RESOURCE_PATH + "/statistics";
	
	private String preparePathId(String id) {
		return String.format(PATH_ID, id);
	}

	@Test
	void crdTest() throws JsonProcessingException {
		RestAssured.defaultParser = Parser.JSON;

		LocalDateTime now = LocalDateTime.now();
		
		Call call = new Call();
		call.setStart(now);
		call.setEnd(now.plusMinutes(10));
		call.setCaller("12");
		call.setCallee("34");
		call.setType(Type.OUTBOUND);

		call = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(call).when().post(PATH).as(Call.class);

		assertNotNull(call.getId());

		call = given().when().get(preparePathId(call.getId())).as(Call.class);

		assertNotNull(call);

		given().when().delete(preparePathId(call.getId())).then().statusCode(HttpStatus.SC_NO_CONTENT);
		given().when().get(preparePathId(call.getId())).then().statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	@Test
	void statisticTest() throws JsonProcessingException {
		Statistic[] statistics = given().when().get(PATH_STATISTICS).as(Statistic[].class);

		assertNotNull(statistics);
	}

}
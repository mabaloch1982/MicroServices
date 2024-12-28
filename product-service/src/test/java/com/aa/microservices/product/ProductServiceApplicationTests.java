package com.aa.microservices.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import io.restassured.RestAssured;

@Import(TestcontainersConfiguration.class)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;

	}
	static {
		mongoDBContainer.start();
	}

	private String createProductJson(String name, String description, int price) {
		return """
                {
                    "name": "%s",
                    "description": "%s",
                    "price": %d
                }
                """.formatted(name, description, price);
	}

	@Test
	void shouldReturnErrorForInvalidProduct() {
		String requestBody = createProductJson("", "", 0);

		RestAssured.
				given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(400);// Assuming 400 Bad Request is returned
				//.body("error", Matchers.notNullValue());
	}

	@Test
	void shouldCreateProduct(){
		String requestBody = """
				{
					"name": "iphone 16",
					"description": "Iphone is a smart phone",
					"price": 1500
				}
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo("iphone 16"))
		        .body("description", Matchers.equalTo("Iphone is a smart phone"))
				.body("price", Matchers.equalTo(1500));

	}
}

package com.aa.microservices.order;

import com.aa.microservices.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWireMock(port = 0) // Random port
class OrderServiceApplicationTests {

@Container
@ServiceConnection
static MySQLContainer<?> mysqlContainer = new MySQLContainer(DockerImageName.parse("mysql:8.3.0"));

@LocalServerPort
private Integer port;

@BeforeEach
void setUp() {

	RestAssured.baseURI = "http://localhost";
	RestAssured.port = port;
}

static {
	mysqlContainer.start();
}

	@Test
	void testConnectionIsEstablished() {

		System.out.println("DB Name: " + mysqlContainer.getDatabaseName());
		System.out.println(mysqlContainer.getUsername());
		System.out.println(mysqlContainer.getPassword());
		System.out.println(mysqlContainer.getEnv());
		Assertions.assertTrue(mysqlContainer.isCreated());
		Assertions.assertTrue(mysqlContainer.isRunning());

	}

	private String createOrderJson(String orderNumber, String skuCode, BigDecimal price, int quantity) {
		return """
                {
                    "orderNumber": "%s",
                    "skuCode": "%s",
                    "price": %f,
                     "quantity": %d
                }
                """.formatted(orderNumber, skuCode, price,quantity);
	}

	@Test
	void shouldReturnErrorForInvalidOrder() {
		String requestBody = createOrderJson("", "", BigDecimal.valueOf(0.0),0);
		System.out.println("requestBody: "+requestBody);
		System.out.println(mysqlContainer.getDatabaseName());
		RestAssured.
				given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(400);// Assuming 400 Bad Request is returned
		//.body("error", Matchers.notNullValue());
	}

	@Test
	void shouldCreateOrder() {
	String skuCode="SKU56789";
	int quantity=2;
		String requestBody = createOrderJson("ORD12347", skuCode, BigDecimal.valueOf(10), quantity);

		InventoryClientStub.stubInventoryClient(skuCode, quantity);

			RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/order")
				.then()
				.statusCode(201)// Assuming 201 Created is returned
				.body("id", Matchers.notNullValue());
		//.body("error", Matchers.notNullValue());
	}
}


package com.aa.microservices.inventory;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InventoryServiceApplicationTests {

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
	@Order(1)
	void testConnectionIsEstablished() {
		System.out.println("DB Name: " + mysqlContainer.getDatabaseName());
		System.out.println(mysqlContainer.getUsername());
		System.out.println(mysqlContainer.getPassword());
		System.out.println(mysqlContainer.getEnv());
		Assertions.assertTrue(mysqlContainer.isCreated());
		Assertions.assertTrue(mysqlContainer.isRunning());

	}

	private String createJson(String skuCode, int quantity) {
		return """
                {
                    "skuCode": "%s",
                     "quantity": %d
                }
                """.formatted(skuCode, quantity);
	}

	@Test
	@Order(2)
	void shouldAddStock() {
		String requestBody = createJson("SKU46789", 100);

		Response response = RestAssured
				.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/inventory")
				.then()
				.statusCode(201)// Assuming 201 Created is returned
				//.body("id", Matchers.notNullValue())
				.extract()
				.response();
		System.out.println(response);
		//.body("error", Matchers.notNullValue());
	}

	@Test
	@Order(3)
	void shouldReturnAll() {
//		String requestBody = createJson("SKU46789", 100);

		var response = RestAssured
				.given()
				.when()
				.get("/api/inventory/all")
				.then()
				.log().all()
				.statusCode(200)
						.extract().response();

		System.out.println(response);

		//.body("error", Matchers.notNullValue());
	}

	@Test
	@Order(4)
	void shouldExistInStock() {
//		String requestBody = createJson("SKU46789", 100);

		var response = RestAssured
				.given()
				.when()
				.get("/api/inventory?skuCode=SKU46789&quantity=10")
				.then()
				.log().all()
				.statusCode(200)
				.extract().response().as(Boolean.class);

		Assertions.assertTrue(response);


		//.body("error", Matchers.notNullValue());
	}

}


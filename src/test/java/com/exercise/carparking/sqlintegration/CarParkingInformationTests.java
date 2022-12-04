package com.exercise.carparking.sqlintegration;

import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.CarParkInformations;
import com.exercise.carparking.service.CarParkInformationService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;

import java.util.Arrays;

@ActiveProfiles("unittest")
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = CarParkingInformationTests.Initializer.class)
class CarParkingInformationTests {

	static String userName = "root";
	static String passWord = "mysql";
	@ClassRule
	public static GenericContainer mySQLContainer = new MySQLContainer("mysql:8.0.31")
			.withDatabaseName("unit-test")
			.withUsername(userName)
			.withPassword(passWord);

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			mySQLContainer.start();
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
					configurableApplicationContext,
					String.format("spring.flyway.url=jdbc:mysql://localhost:%s", mySQLContainer.getFirstMappedPort()),
					String.format("spring.flyway.user=%s", userName),
					String.format("spring.flyway.password=%s", passWord),
					String.format("spring.datasource.query.jdbc-url=jdbc:mysql://localhost:%s/carparking", mySQLContainer.getFirstMappedPort()),
					String.format("spring.datasource.query.usernamer=%s", userName),
					String.format("spring.datasource.query.password=%s", passWord),
					String.format("spring.datasource.register.jdbc-url=jdbc:mysql://localhost:%s/carparking", mySQLContainer.getFirstMappedPort()),
					String.format("spring.datasource.register.usernamer=%s", userName),
					String.format("spring.datasource.register.password=%s", passWord)
			);
		}
	}

	CarParkInformationService carParkInformationService;

	@Before
	@Autowired
	void setup(CarParkInformationService carParkInformationService) {
		this.carParkInformationService = carParkInformationService;
	}

	@Test
	@Sql({"classpath:testsql/insertCarParkInformation.sql"})
	void carparkInformationAllTest() {
		CarParkInformations carParkInformations = carParkInformationService.findAll();
		Assertions.assertTrue(!carParkInformations.isEmpty(), "unexpected empty carpark information");
		Assertions.assertEquals(carParkInformations.values(),
				Arrays.asList(
						new CarParkInformation("ACB", "BLK 270/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49),
						new CarParkInformation("BJ18", "BLK 401-408 FAJAR ROAD", 20743.28, 40326.26),
						new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95),
						new CarParkInformation("Y68M", "BLK 504 YISHUN ST 51", 29255.01, 44464.6)
				)
		, "incorrect car park information data");
	}

	@Test
	@Sql({"classpath:testsql/insertCarParkInformation.sql"})
	void findByCarParkNoTest() {
		CarParkInformation carParkInformation = carParkInformationService.findBy("CKT1");
		Assertions.assertTrue(!carParkInformation.isEmpty());
		Assertions.assertEquals(carParkInformation, new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95));
	}

	@Test
	@Sql({"classpath:testsql/insertCarParkInformation.sql"})
	void updateInformationTest() {
		CarParkInformations carParkInformations = new CarParkInformations(Arrays.asList(
				new CarParkInformation("ACB", "BLK 222/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49),
				new CarParkInformation("BJ18", "BLK 401-408 FAJAR ROAD", 10743.28, 40326.26),
				new CarParkInformation("DKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)
		));

		carParkInformationService.update(carParkInformations);

		CarParkInformations retCarParkInformations = carParkInformationService.findAll();
		Assertions.assertTrue(!retCarParkInformations.isEmpty(), "unexpected empty carpark information");
		Assertions.assertEquals(retCarParkInformations,
				carParkInformations,
				"incorrect car park information data");
	}

	@AfterEach
	public void clearData() {
		carParkInformationService.clear();
		Assertions.assertTrue(carParkInformationService.findAll().isEmpty(), "car park information still have remaining items after clear");}

}

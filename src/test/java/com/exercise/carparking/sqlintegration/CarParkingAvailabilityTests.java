package com.exercise.carparking.sqlintegration;

import com.exercise.carparking.domain.CarParkAvailabilities;
import com.exercise.carparking.domain.CarParkAvailability;
import com.exercise.carparking.domain.CarParkInformation;
import com.exercise.carparking.domain.Pageable;
import com.exercise.carparking.service.CarParkAvailabilityService;
import com.exercise.carparking.service.CarParkInformationService;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@ContextConfiguration(initializers = CarParkingAvailabilityTests.Initializer.class)
class CarParkingAvailabilityTests {

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
	CarParkAvailabilityService carParkAvailabilityService;

	@Before
	@Autowired
	void setup(CarParkInformationService carParkInformationService, CarParkAvailabilityService carParkAvailabilityService) {
		this.carParkInformationService = carParkInformationService;
		this.carParkAvailabilityService = carParkAvailabilityService;
	}

	@Test
	@Sql({
			"classpath:testsql/insertCarParkInformation.sql",
			"classpath:testsql/insertCarParkAvailability.sql"
	})
	void getCarParkAvailabilityAllTest() {
		CarParkAvailabilities carParkAvailabilities = carParkAvailabilityService.findAll();
		Assertions.assertFalse(carParkAvailabilities.isEmpty(), "unexpected empty carpark availability");
		Assertions.assertEquals(carParkAvailabilities.values(),
				Arrays.asList(
						new CarParkAvailability(93, 55, "C",
								new CarParkInformation("ACB", "BLK 270/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49)),
						new CarParkAvailability(324, 0, "C",
								new CarParkInformation("BJ18", "BLK 401-408 FAJAR ROAD", 20743.28, 40326.26)),
						new CarParkAvailability(104, 33, "C",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)),
						new CarParkAvailability(114, 25, "D",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95))

				)
		, "incorrect car park availability data");
	}

	@Test
	@Sql({
			"classpath:testsql/insertCarParkInformation.sql",
			"classpath:testsql/insertCarParkAvailability.sql"
	})
	void findByCarParkNoAndLotTypeTest() {
		CarParkAvailability carParkAvailability = carParkAvailabilityService.findBy("CKT1", "C");
		Assertions.assertFalse(carParkAvailability.isEmpty());
		Assertions.assertEquals(carParkAvailability, new CarParkAvailability(104, 33, "C",
				new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)));

		CarParkAvailability emptyCarParkAvailability = carParkAvailabilityService.findBy("BJ18", "C");
		Assertions.assertNull(emptyCarParkAvailability);
	}

	@Test
	@Sql({
			"classpath:testsql/insertCarParkInformation.sql",
			"classpath:testsql/insertCarParkAvailability.sql"
	})
	void getCarParkAvailabilityByCoordinationTest() {
		CarParkAvailabilities carParkAvailabilities = carParkAvailabilityService.getCarParkAvailabilities(1.37326, 103.897, new Pageable(1, 3));
		Assertions.assertFalse(carParkAvailabilities.isEmpty());
		Assertions.assertEquals(carParkAvailabilities.values(),
				Arrays.asList(
						new CarParkAvailability(104, 33, "C",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)),
						new CarParkAvailability(114, 25, "D",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)),
						new CarParkAvailability(93, 55, "C",
								new CarParkInformation("ACB", "BLK 270/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49))

				)
				, "incorrect car park availability data");
	}

	@Test
	@Sql({
			"classpath:testsql/insertCarParkInformation.sql",
			"classpath:testsql/insertCarParkAvailability.sql"
	})
	void updateAvailabilityTest() {
		CarParkAvailabilities carParkAvailabilities = new CarParkAvailabilities(Arrays.asList(
				new CarParkAvailability(93, 55, "C",
						new CarParkInformation("ACB", "BLK 270/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49)),
				new CarParkAvailability(324, 10, "C",
						new CarParkInformation("BJ18", "BLK 401-408 FAJAR ROAD", 20743.28, 40326.26)),
				new CarParkAvailability(104, 0, "D",
						new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95))

		));

		carParkAvailabilityService.update(carParkAvailabilities);

		CarParkAvailabilities retCarParkAvailabilities = carParkAvailabilityService.findAll();
		Assertions.assertFalse(retCarParkAvailabilities.isEmpty(), "unexpected empty carpark availability");
		Assertions.assertEquals(retCarParkAvailabilities.values(),
				Arrays.asList(
						new CarParkAvailability(93, 55, "C",
								new CarParkInformation("ACB", "BLK 270/271 ALBERT CENTRE BASEMENT CAR PARK", 30314.79, 31490.49)),
						new CarParkAvailability(324, 10, "C",
								new CarParkInformation("BJ18", "BLK 401-408 FAJAR ROAD", 20743.28, 40326.26)),
						new CarParkAvailability(104, 33, "C",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95)),
						new CarParkAvailability(104, 0, "D",
								new CarParkInformation("CKT1", "BLK BETWEEN 404 & 426 CHOA CHU KANG AVENUE 1/4", 17457.27, 40253.95))

				)
				, "incorrect car park availability data");
	}

	@AfterEach
	public void clearData() {
		carParkInformationService.clear();
		carParkAvailabilityService.deleteIfNotExist();

		Assertions.assertTrue(carParkInformationService.findAll().isEmpty(), "car park information still have remaining items after clear");
		Assertions.assertTrue(carParkAvailabilityService.findAll().isEmpty(), "car park available still have remaining items after clear");
	}

}

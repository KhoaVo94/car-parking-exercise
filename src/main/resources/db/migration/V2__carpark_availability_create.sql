DROP TABLE IF EXISTS `carparking`.`carpark_availability`;

CREATE TABLE `carparking`.`carpark_availability` (
    `total_lots` INT NOT NULL,
    `available_lots` INT NOT NULL,
    `lot_type` VARCHAR(5) NOT NULL,
    `car_park_no` VARCHAR(10) NOT NULL,
    `updated_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`car_park_no`, `lot_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
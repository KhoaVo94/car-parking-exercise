DROP TABLE IF EXISTS `carparking`.`carpark_info`;

CREATE TABLE `carparking`.`carpark_info` (
    `car_park_no` VARCHAR(10) NOT NULL,
    `address` VARCHAR(200) NOT NULL,
    `latitude` DOUBLE NOT NULL,
    `longitude` DOUBLE NOT NULL,
    PRIMARY KEY (`car_park_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
package com.exercise.carparking.worker.scheduler;

import com.exercise.carparking.carparkingapi.service.CarParkAvailabilityService;
import com.exercise.carparking.carparkingapi.service.CarParkInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    CarParkInformationService carParkInformationService;
    CarParkAvailabilityService carParkAvailabilityService;

    @Autowired
    public ScheduledTasks(CarParkInformationService carParkInformationService,
                         CarParkAvailabilityService carParkAvailabilityService) {
        this.carParkInformationService = carParkInformationService;
        this.carParkAvailabilityService = carParkAvailabilityService;
    }

    @Scheduled(cron = "10 */1 * * * *")
    public void updateCarParkingAvailability() {
        carParkAvailabilityService.updateWithLatestData().block();
        carParkAvailabilityService.deleteIfNotExist();
    }

    @Scheduled(cron = "0 0 4 */1 * *")
    public void updateCarParkingInformation() {
        carParkInformationService.updateWithLatestData(2000).block();
    }
}

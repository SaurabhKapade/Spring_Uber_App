package com.example.Uber.controller;

import com.example.Uber.dto.DriverLocationDto;
import com.example.Uber.dto.NearbyDriversRequest;
import com.example.Uber.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody  DriverLocationDto driverLocationDto){
        Boolean saved = locationService.saveDriverLocation(driverLocationDto.getDriverId().toString(), driverLocationDto.getLattitude(), driverLocationDto.getLongitude());
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/nearbyDrivers")
    public ResponseEntity<List<DriverLocationDto>> getNearbyDrivers(@RequestBody NearbyDriversRequest nearbyDriver){
        List<DriverLocationDto> nearbyDrivers = locationService.getNearbyDrivers(nearbyDriver.getLatitude(), nearbyDriver.getLongitude(), nearbyDriver.getRadius());
        return ResponseEntity.ok(nearbyDrivers);
    }
}

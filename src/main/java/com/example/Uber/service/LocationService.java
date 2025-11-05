package com.example.Uber.service;

import com.example.Uber.dto.DriverLocationDto;

import java.util.List;

public interface LocationService {
    Boolean saveDriverLocation(String driverId,Double lattitude,Double longitude);
    List<DriverLocationDto> getNearbyDrivers(Double lattitude, Double longitude, Double radius);
}

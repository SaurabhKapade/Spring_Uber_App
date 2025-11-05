package com.example.Uber.service.impl;

import com.example.Uber.dto.DriverLocationDto;
import com.example.Uber.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisLocationService implements LocationService {
    private final String DRIVER_GEO_OPS_KEY = "driver:geo";
    private final StringRedisTemplate stringRedisTemplate;
    @Override
    public Boolean saveDriverLocation(String driverId, Double lattitude, Double longitude) {
        GeoOperations<String,String> geoOperations = stringRedisTemplate.opsForGeo();
        geoOperations.add(DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<>(driverId,new Point(lattitude,longitude))
                );
        return true;
    }

    @Override
    public List<DriverLocationDto> getNearbyDrivers(Double lattitude, Double longitude, Double radius) {
        GeoOperations<String,String> geoOperations = stringRedisTemplate.opsForGeo();
        Distance distanceRadius = new Distance(radius, Metrics.KILOMETERS);
        Circle circle = new Circle(new Point(lattitude,longitude),radius);
        GeoResults<GeoLocation<String>> results = geoOperations.radius(DRIVER_GEO_OPS_KEY,circle);
        List<DriverLocationDto> driverLocations = new ArrayList<>();
        for(GeoResult<GeoLocation<String>> result :results){
            Point point = geoOperations.position(DRIVER_GEO_OPS_KEY, result.getContent().getName()).get(0);
            DriverLocationDto driverLocation = DriverLocationDto.builder()
                    .driverId(result.getContent().getName())
                    .lattitude(point.getY())
                    .longitude(point.getX())
                    .build();
            driverLocations.add(driverLocation);
        }
        return driverLocations;
    }
}

package com.example.Uber.service;

import java.math.BigDecimal;

public interface FareCalculateService {
    BigDecimal calculateFare(double lat1, double lon1,double lat2, double lon2);
}

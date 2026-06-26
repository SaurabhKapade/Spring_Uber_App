package com.example.Uber.service.impl;

import com.example.Uber.service.FareCalculateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class FareCalculateServiceImpl implements FareCalculateService {

    @Override
    public BigDecimal calculateFare(double lat1, double lon1,double lat2, double lon2) {

        final double EARTH_RADIUS = 6371; // KM
        final BigDecimal RATE_PER_KM = BigDecimal.valueOf(15);
        final BigDecimal GST_RATE = BigDecimal.valueOf(0.18);

        // Haversine formula
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        double distance = EARTH_RADIUS * c;

        // Convert distance to BigDecimal
        BigDecimal distanceBD = BigDecimal.valueOf(distance);

        // Base Fare = Distance × Rate
        BigDecimal baseFare = distanceBD.multiply(RATE_PER_KM);

        // GST = BaseFare × GST_RATE
        BigDecimal gstAmount = baseFare.multiply(GST_RATE);

        // Final Fare
        BigDecimal finalFare = baseFare.add(gstAmount);

        return finalFare.setScale(2, RoundingMode.HALF_UP);
    }
}

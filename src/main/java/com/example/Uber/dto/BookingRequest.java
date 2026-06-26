package com.example.Uber.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {
    
    @NotNull(message = "Passenger ID is required")
    private Long passengerId;
    
    private Long driverId;
    
    @NotNull(message = "Pickup location is required")
    private Double pickupLocationLattitude;

    @NotNull(message = "Pickup location is required")
    private Double pickupLocationLongitude;
    
    @NotNull(message = "Dropoff location is required")
    private Double dropoffLocationLattitude;

    @NotNull(message = "Dropoff location is required")
    private Double dropoffLocationLongitude;

    private LocalDateTime scheduledPickupTime;

}


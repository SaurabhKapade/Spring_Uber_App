package com.example.Uber.mapper;

import com.example.Uber.dto.BookingRequest;
import com.example.Uber.dto.BookingResponse;
import com.example.Uber.entity.Booking;
import com.example.Uber.entity.Driver;
import com.example.Uber.entity.Passenger;
import com.example.Uber.service.FareCalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final FareCalculateService fareCalculateService;
    public Booking toEntity(BookingRequest request, Passenger passenger, Driver driver) {
        Booking.BookingStatus status = driver != null ? Booking.BookingStatus.CONFIRMED : Booking.BookingStatus.PENDING;
        
        return Booking.builder()
                .passenger(passenger)
                .driver(driver)
                .pickupLocationLattitude(request.getPickupLocationLattitude())
                .pickupLocationLongitude(request.getPickupLocationLongitude())
                .dropoffLocationLongitude(request.getDropoffLocationLongitude())
                .dropoffLocationLongitude(request.getDropoffLocationLongitude())
                .fare(
                        fareCalculateService.calculateFare(
                                request.getPickupLocationLattitude(),
                                request.getPickupLocationLongitude(),
                                request.getDropoffLocationLattitude(),
                                request.getDropoffLocationLongitude()
                        )
                )
                .status(status)
                .scheduledPickupTime(request.getScheduledPickupTime())
                .build();
    }
    
    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .passengerId(booking.getPassenger() != null ? booking.getPassenger().getId() : null)
                .passengerName(booking.getPassenger() != null ? booking.getPassenger().getName() : null)
                .driverId(booking.getDriver() != null ? booking.getDriver().getId() : null)
                .driverName(booking.getDriver() != null ? booking.getDriver().getName() : null)
                .pickupLocationLattitude(booking.getPickupLocationLattitude())
                .pickupLocationLongitude(booking.getPickupLocationLongitude())
                .dropoffLocationLattitude(booking.getDropoffLocationLattitude())
                .dropoffLocationLongitude(booking.getPickupLocationLongitude())
                .status(booking.getStatus())
                .fare(booking.getFare())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .scheduledPickupTime(booking.getScheduledPickupTime())
                .actualPickupTime(booking.getActualPickupTime())
                .completedAt(booking.getCompletedAt())
                .build();
    }
    
    public void updateEntity(Booking booking, BookingRequest request, Passenger passenger, Driver driver) {
        booking.setPassenger(passenger);
        booking.setDriver(driver);
        booking.setDropoffLocationLattitude(request.getDropoffLocationLattitude());
        booking.setPickupLocationLongitude(request.getPickupLocationLongitude());
        booking.setDropoffLocationLattitude(request.getDropoffLocationLattitude());
        booking.setDropoffLocationLongitude(request.getDropoffLocationLongitude());
        booking.setFare(booking.getFare());
        booking.setScheduledPickupTime(request.getScheduledPickupTime());
        
        // Update status based on driver assignment
        if (driver != null && booking.getStatus() == Booking.BookingStatus.PENDING) {
            booking.setStatus(Booking.BookingStatus.CONFIRMED);
        }
    }
}


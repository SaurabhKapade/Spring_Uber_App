package com.example.Uber.service.impl;

import com.example.Uber.dto.ReviewRequest;
import com.example.Uber.dto.ReviewResponse;
import com.example.Uber.entity.*;
import com.example.Uber.mapper.ReviewMapper;
import com.example.Uber.repository.*;
import com.example.Uber.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepository;

    @Override
    public ReviewResponse addReview(ReviewRequest reviewRequest) {

        Passenger passenger = passengerRepository.findById(reviewRequest.getPassengerId())
                .orElseThrow(() -> new RuntimeException("Passenger not found"));
        Driver driver = driverRepository.findById(reviewRequest.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        Booking booking = bookingRepository.findById(reviewRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));


        Review review = ReviewMapper.toEntity(reviewRequest, passenger, driver, booking);

        Review savedReview = reviewRepository.save(review);

        return ReviewMapper.toResponse(savedReview);
    }
}

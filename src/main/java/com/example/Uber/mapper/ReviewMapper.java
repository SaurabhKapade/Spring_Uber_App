package com.example.Uber.mapper;

import com.example.Uber.dto.ReviewRequest;
import com.example.Uber.dto.ReviewResponse;
import com.example.Uber.entity.*;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    // If you only have IDs (like from ReviewRequest)
    public static Review toEntity(ReviewRequest reviewRequest, Passenger passenger, Driver driver, Booking booking) {
        return Review.builder()
                .passenger(passenger)
                .driver(driver)
                .booking(booking)
                .rating(reviewRequest.getRating())
                .comment(reviewRequest.getComment())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getId())
                .passengerId(review.getPassenger().getId())
                .driverId(review.getDriver().getId())
                .bookingId(review.getBooking().getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}

package com.example.Uber.controller;

import com.example.Uber.dto.ReviewRequest;
import com.example.Uber.dto.ReviewResponse;
import com.example.Uber.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> markReview(@RequestBody ReviewRequest reviewRequest){
        ReviewResponse response = reviewService.addReview(reviewRequest);
        return ResponseEntity.ok(response);
    }
}

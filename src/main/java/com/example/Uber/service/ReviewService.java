package com.example.Uber.service;

import com.example.Uber.dto.ReviewRequest;
import com.example.Uber.dto.ReviewResponse;

public interface ReviewService {
    ReviewResponse addReview(ReviewRequest reviewRequest);
}

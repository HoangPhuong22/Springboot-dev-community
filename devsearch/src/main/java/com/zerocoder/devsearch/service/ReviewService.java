package com.zerocoder.devsearch.service;

import com.zerocoder.devsearch.entity.Review;

import java.util.List;

public interface ReviewService {
    void saveReview(Review review);
    Review getReview(Long id);
    List<Review> getAllReviews();
    void updateReview(Review review);
    void deleteReview(Long id);
}

package com.zerocoder.devsearch.dao;

import com.zerocoder.devsearch.entity.Review;

import java.util.List;

public interface ReviewDAO {
    void saveReview(Review review);
    Review getReview(Long id);
    List<Review> getAllReviews();
    void updateReview(Review review);
    void deleteReview(Long id);
}

package com.zerocoder.devsearch.serviceImpl;

import com.zerocoder.devsearch.dao.ReviewDAO;
import com.zerocoder.devsearch.entity.Review;
import com.zerocoder.devsearch.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewDAO reviewDAO;
    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }
    @Override
    @Transactional
    public void saveReview(Review review) {
        reviewDAO.saveReview(review);
    }

    @Override
    public Review getReview(Long id) {
        return reviewDAO.getReview(id);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }

    @Override
    @Transactional
    public void updateReview(Review review) {
        reviewDAO.updateReview(review);
    }

    @Override
    @Transactional
    public void deleteReview(Long id) {
        reviewDAO.deleteReview(id);
    }
}

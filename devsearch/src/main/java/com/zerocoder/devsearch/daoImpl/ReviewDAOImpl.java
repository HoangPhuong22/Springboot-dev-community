package com.zerocoder.devsearch.daoImpl;

import com.zerocoder.devsearch.dao.ReviewDAO;
import com.zerocoder.devsearch.entity.Review;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO {
    private EntityManager entityManager;
    @Autowired
    public ReviewDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void saveReview(Review review) {
        entityManager.persist(review);
    }

    @Override
    public Review getReview(Long id) {
        return entityManager.find(Review.class, id);
    }

    @Override
    public List<Review> getAllReviews() {
        try {
            List<Review> reviews = entityManager.createQuery("from Review r left join fetch r.profile left join fetch r.project").getResultList();
            return reviews;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void updateReview(Review review) {
        entityManager.merge(review);
    }

    @Override
    public void deleteReview(Long id) {
        Review review = entityManager.find(Review.class, id);
        entityManager.remove(review);
    }
}

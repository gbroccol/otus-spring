package ru.otus.spring.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.spring.model.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ReviewRepositoryJpa implements ReviewRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Review save(Review review) {
        if (review.getReviewId() <= 0) {
            em.persist(review);
            return review;
        }
        return em.merge(review);
    }

    @Override
    public Optional<Review> findById(long id) {
        return Optional.ofNullable(em.find(Review.class, id));
    }

    @Override
    public List<Review> findByBookId(long bookId) {
        var query = em.createQuery("select r from Review r WHERE book_id = :bookId", Review.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                        "from Review r " +
                        "where r.reviewId = :reviewId");
        query.setParameter("reviewId", id);
        query.executeUpdate();
    }

}

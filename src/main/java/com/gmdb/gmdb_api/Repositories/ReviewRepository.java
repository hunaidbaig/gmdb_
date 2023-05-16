package com.gmdb.gmdb_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmdb.gmdb_api.Entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    
}

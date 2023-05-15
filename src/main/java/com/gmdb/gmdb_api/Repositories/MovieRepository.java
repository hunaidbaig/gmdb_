package com.gmdb.gmdb_api.Repositories;
import com.gmdb.gmdb_api.Entities.Movies;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MovieRepository extends JpaRepository<Movies, Integer> {
    
}

package com.gmdb.gmdb_api.Repositories.RepoImplement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmdb.gmdb_api.Entities.Reviewer;

public interface ReviewerRepository extends JpaRepository<Reviewer, Integer> {
    
}

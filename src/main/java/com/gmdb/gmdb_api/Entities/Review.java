package com.gmdb.gmdb_api.Entities;

import java.sql.Date;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reviewId;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonBackReference
    private Movies movies;
    
    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;
    
    
    private String review_text;

    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate last_modified;
    
    public Review(Movies movies, String review_text, LocalDate date) {
        this.movies = movies;
        this.review_text = review_text;
    }
    public Review(Integer reviewId, Movies movies, Reviewer reviewer, String review_text, LocalDate last_modified) {
        this.reviewId = reviewId;
        this.movies = movies;
        this.reviewer = reviewer;
        this.review_text = review_text;
        this.last_modified = last_modified;
    }
}

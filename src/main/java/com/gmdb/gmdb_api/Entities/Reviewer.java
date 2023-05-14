package com.gmdb.gmdb_api.Entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "reviewers")
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "username")
    private String username;

    @Column(name = "date_joined")
    private LocalDate dateJoined;

    @Column(name = "num_reviews")
    private Integer numReviews;

}

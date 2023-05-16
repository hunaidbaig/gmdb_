package com.gmdb.gmdb_api.Entities;

import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewer_id")
    private Integer reviewerId;

    @Column(name = "username")
    private String username;

    @Column(columnDefinition = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateJoined;
    private Integer numReviews;

    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.PERSIST)
    private List<Review> reviews;


    public Reviewer(String username) {
        this.username = username;
        this.dateJoined = LocalDate.now();
        this.numReviews = 0;
    }

    public Reviewer(String username, LocalDate dateJoined, Integer numReviews) {
        this.username = username;
        this.dateJoined = dateJoined;
        this.numReviews = numReviews;
    }

}

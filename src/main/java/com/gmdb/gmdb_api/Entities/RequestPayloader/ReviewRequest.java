package com.gmdb.gmdb_api.Entities.RequestPayloader;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private Integer movieId;
    private Integer reviewId;
    private Integer reviewerId;
    private String reviewText;

}

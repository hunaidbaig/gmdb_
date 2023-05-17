package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Repositories.ReviewRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewerRepository reviewerRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/save")
    public Review insertReview(@RequestBody Review review){
        return this.reviewRepository.save(review);
    }

    @GetMapping("")
    public List<Review> getAllReview(){
        return this.reviewRepository.findAll();
    }

    @GetMapping("/{id}")
    public Review getReview(Integer id){
        return this.reviewRepository.getReferenceById(id);
    }
}

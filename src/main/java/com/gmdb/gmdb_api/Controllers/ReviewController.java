package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Entities.RequestPayloader.ReviewRequest;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Repositories.ReviewRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;


@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;

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


    @PutMapping("/{id}")
    public ResponseEntity<Review> updatReview(@PathVariable Integer id, @RequestBody ReviewRequest request){
        Optional<Review> find = this.reviewRepository.findById(id);

        if(!find.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Review review1 = find.get();

        review1.setReview_text(request.getReviewText());
        find.get().getReviewer().getReviewerId();
        this.reviewRepository.save(review1);
        return ResponseEntity.ok(review1) ;
    }
}

package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Entities.RequestPayloader.ReviewRequest;
import com.gmdb.gmdb_api.Repositories.ReviewRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;


@RestController
@RequestMapping("/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerRepository reviewerRepo;

    @Autowired
    private ReviewRepository reviewRepo;

    @PostMapping("/save")
    public Reviewer insertReview(@RequestBody Reviewer reviewer){
        return this.reviewerRepo.save(reviewer);
    }

    @GetMapping("/{id}")
    public Optional<Reviewer> getReviewerDetail(@PathVariable Integer id){
        return this.reviewerRepo.findById(id);
    }


    @DeleteMapping("/{id}/reviews/{review_id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id, @PathVariable Integer review_id){
        this.reviewRepo.deleteById(review_id);
        return ResponseEntity.ok("Review has deleted");
    }

    @PutMapping("/{id}/reviews/{review_id}")
    public Review updateReview(@PathVariable Integer id, @PathVariable Integer review_id, @RequestBody ReviewRequest reviewRequest){
        Review review = this.reviewRepo.getReferenceById(review_id);

        review.setReview_text(reviewRequest.getReviewText());
        return this.reviewRepo.save(review);
    }

    // @GetMapping("")
    // public List<Reviewer> getAllReviewerDetail(){
    //     return this.reviewerRepo.findAll();
    // }
}

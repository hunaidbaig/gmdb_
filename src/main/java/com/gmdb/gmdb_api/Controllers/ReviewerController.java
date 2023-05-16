package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;


@RestController
@RequestMapping("/reviewers")
public class ReviewerController {

    @Autowired
    private ReviewerRepository reviewerRepo;

    @PostMapping("")
    public Reviewer insertReview(@RequestBody Reviewer reviewer){
        return this.reviewerRepo.save(reviewer);
    }

    @GetMapping("/{id}")
    public Optional<Reviewer> getReviewerDetail(@PathVariable Integer id){
        return this.reviewerRepo.findById(id);
    }

    // @GetMapping("")
    // public List<Reviewer> getAllReviewerDetail(){
    //     return this.reviewerRepo.findAll();
    // }
}

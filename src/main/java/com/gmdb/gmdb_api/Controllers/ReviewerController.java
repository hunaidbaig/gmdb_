package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/save")
    public Reviewer insertReview(@RequestBody Reviewer reviewer){
        return this.reviewerRepo.save(reviewer);
    }

    @GetMapping("")
    public List<Reviewer> getAllReview(){
        return this.reviewerRepo.findAll();
    }
}

package com.gmdb.gmdb_api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Services.MoviesService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MoviesService movieService;

    @PostMapping("/save")
    public ResponseEntity<Movies> insertMovie(@RequestBody Movies movie){
        this.movieService.insertMovie(movie);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("")
    public List<Movies> getAllMovies(Integer id){
       return this.movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Optional<Movies> getMovie(@PathVariable Integer id){
        return this.movieService.getMovie(id);
    }



    
}

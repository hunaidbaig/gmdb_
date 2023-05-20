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

import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Services.MoviesService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository repo;

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

    @PutMapping("/{id}")
    public ResponseEntity<Movies> updateMovieByAdmin(@PathVariable Integer id, @RequestBody Movies updateMovie){
        Optional<Movies> movieExist  = this.repo.findById(id);

        if(!movieExist.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Movies movie = movieExist.get();

        movie.setId(updateMovie.getId());
        movie.setTitle(updateMovie.getTitle());
        movie.setGener(updateMovie.getGener());
        movie.setRuntime(updateMovie.getRuntime());
        movie.setYear(updateMovie.getYear());

        this.movieService.insertMovie(movie);

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public Optional<Movies> getMovie(@PathVariable Integer id){
        return this.movieService.getMovie(id);
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Integer id){
        this.repo.deleteById(id);

        return "Movie is deleted";

    }



    
}

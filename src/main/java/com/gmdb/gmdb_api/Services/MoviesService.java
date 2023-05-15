package com.gmdb.gmdb_api.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.MovieServiceRepo;

@Service
public class MoviesService implements MovieServiceRepo {

    private MovieRepository movieRepo;

    public MoviesService(MovieRepository repo){
        this.movieRepo = repo;
    }

    @Override
    public  void insertMovie(Movies movie){
        //Movie movie2 = new Movie(movie.getMovie_id(),movie.getTitle(), movie.getYear(), movie.getGener(), movie.getRuntime());
        this.movieRepo.save(movie);
    }
    @Override
    public Movies getMovie(Integer id) {
        return this.movieRepo.getReferenceById(id);
    }

    @Override
    public List<Movies> getAllMovies() {
        return this.movieRepo.findAll();
    }
    
}

package com.gmdb.gmdb_api.Services;

import java.util.List;

import com.gmdb.gmdb_api.Entities.Movie;

public interface MovieService {
    Movie getMovie(Long id);
    List<Movie> getAllMovies();
}

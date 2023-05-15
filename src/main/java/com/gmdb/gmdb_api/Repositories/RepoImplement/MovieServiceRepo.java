package com.gmdb.gmdb_api.Repositories.RepoImplement;

import java.util.List;

import com.gmdb.gmdb_api.Entities.Movies;

public interface MovieServiceRepo {
    void insertMovie(Movies movie);
    Movies getMovie(Integer id);
    List<Movies> getAllMovies();
}

package com.gmdb.gmdb_api;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
// import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmdb.gmdb_api.Controllers.MovieController;
import com.gmdb.gmdb_api.Controllers.ReviewController;
import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.MovieServiceRepo;
import com.gmdb.gmdb_api.Services.MoviesService;
import org.springframework.test.web.servlet.setup.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;




@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class GmdbApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MoviesService movieRepo;

    @InjectMocks
    private MovieController movieController;

	@InjectMocks
    private ReviewController reviewController;

    @Autowired
    private JacksonTester<Movies> movie;

	@Autowired
    private JacksonTester<Review> review;

    @Autowired
    private JacksonTester<List<Movies>> moviesList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

	// 1. As a user
    //    I can GET a list of movies from GMDB that includes Movie ID | Movie Title | Year Released | Genre | Runtime
    //    so that I can see the list of available movies.
    @Test
    public void getAllMovies() throws Exception {
        Movies movie1 = new Movies("John Wick 4", 2023, "Thriller", 176);
        Movies movie2 = new Movies("John Wick 3", 2019, "Suspense", 150);

        List<Movies> mvList = new ArrayList<>();
        mvList.add(movie1);
        mvList.add(movie2);

        when(movieRepo.getAllMovies()).thenReturn(mvList);

        mvc.perform(MockMvcRequestBuilders.get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesList.write(mvList).getJson()));
    }


	// 2. As a user
    //    I can provide a movie ID and get back the record shown in story 1, plus a list of reviews that contains Review ID | Movie ID | Reviewer ID | Review Text | DateTime last modified
    //    so that I can read the reviews for a movie.
	@Test
    public void getListOfReviews() throws Exception {
        Movies movie1 = new Movies("John Wick 4", 2023, "Thriller", 176);
		Review review1 = new Review(movie1, "nice movies", LocalDateTime.now());	

        when(reviewController.getReview(1)).thenReturn(review1);

        mvc.perform(MockMvcRequestBuilders.get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(review.write(review1).getJson()));
    }

}

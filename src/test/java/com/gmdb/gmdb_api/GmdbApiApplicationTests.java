package com.gmdb.gmdb_api;

import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.*;
// import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gmdb.gmdb_api.Controllers.MovieController;
import com.gmdb.gmdb_api.Controllers.ReviewController;
import com.gmdb.gmdb_api.Controllers.ReviewerController;
import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Repositories.ReviewRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;
import com.gmdb.gmdb_api.Services.MoviesService;
import org.springframework.test.web.servlet.setup.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class GmdbApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockMvc mvcReviewer;

    @MockBean
    private MoviesService movieRepo;

    @MockBean
    private ReviewRepository reviewRepo;

    @MockBean
    private ReviewerRepository reviewerRepo;

    @InjectMocks
    private MovieController movieController;

    @InjectMocks
    private ReviewController reviewController;

	@InjectMocks
    private ReviewerController reviewerController;

    @Autowired
    private JacksonTester<Movies> movie;

	@Autowired
    private JacksonTester<Review> review;

    @Autowired
    private JacksonTester<Reviewer> reviewer;

    @Autowired
    private JacksonTester<List<Movies>> moviesList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mvcReviewer = MockMvcBuilders.standaloneSetup(reviewerController).build();
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
        Movies movie1 = new Movies(1,"John Wick 4", 2023, "Thriller", 176);
        Reviewer reviewer1 = new Reviewer("Hunaid",LocalDate.now(), 1);

		Review review1 = new Review(movie1, "nice movies", LocalDate.now());	
		Review review2 = new Review(movie1, "Behtreen movie", LocalDate.now());	

        review1.setReviewer(reviewer1);
        review2.setReviewer(reviewer1);
        
        List<Review> listOfReviews = List.of(review1, review2);
        movie1.setReviews(listOfReviews);

        when(movieRepo.getMovie(1)).thenReturn(Optional.of(movie1));

        mvc.perform(MockMvcRequestBuilders.get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(movie.write(movie1).getJson()));
    }


    @Test
    public void getReviwerDetail() throws Exception {
        Reviewer reviewer1 = new Reviewer("Huda", LocalDate.now(), 1);

        when(reviewerRepo.findById(1)).thenReturn(Optional.of(reviewer1));

        mvcReviewer.perform(MockMvcRequestBuilders.get("/reviewers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(reviewer.write(reviewer1).getJson()));
    }

    @Test
    public void createReviewer() throws Exception {
        Reviewer reviewer1 = new Reviewer("Huda");

        when(reviewerRepo.save(reviewer1)).thenReturn(reviewer1);

        mvcReviewer.perform(MockMvcRequestBuilders.post("/reviewers/save"))
            .content(reviewer.write(reviewer1).getJson())
            .andExpect(content().json(reviewer.write(reviewer1).getJson()))
            .andExpect(status().isOk());
    }

}

package com.gmdb.gmdb_api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.apache.tomcat.util.http.parser.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockHttpServletRequest;
// import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.gmdb.gmdb_api.Controllers.MovieController;
import com.gmdb.gmdb_api.Controllers.ReviewController;
import com.gmdb.gmdb_api.Controllers.ReviewerController;
import com.gmdb.gmdb_api.Entities.Movies;
import com.gmdb.gmdb_api.Entities.Review;
import com.gmdb.gmdb_api.Entities.Reviewer;
import com.gmdb.gmdb_api.Entities.RequestPayloader.ReviewRequest;
import com.gmdb.gmdb_api.Repositories.MovieRepository;
import com.gmdb.gmdb_api.Repositories.ReviewRepository;
import com.gmdb.gmdb_api.Repositories.RepoImplement.ReviewerRepository;
import com.gmdb.gmdb_api.Services.MoviesService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.springframework.test.web.client.match.MockRestRequestMatchers;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class GmdbApiApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MockMvc mvcReviewer;

    @Autowired
    private MockMvc mvcReview;

    @Mock
    private MovieRepository repoForMovies;

    @Mock
    private MoviesService movieRepo;

    @Mock
    private ReviewRepository reviewRepo;

    @Mock
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
    private JacksonTester<Review> reviewJson;

    @Autowired
    private JacksonTester<ReviewRequest> reviewRequest;

    @Autowired
    private JacksonTester<Reviewer> reviewer;

    @Autowired
    private JacksonTester<List<Movies>> moviesList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(movieController).build();
        mvcReviewer = MockMvcBuilders.standaloneSetup(reviewerController).build();
        mvcReview = MockMvcBuilders.standaloneSetup(reviewController).build();
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

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reviewers/save")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(reviewer.write(reviewer1).getJson());
            
        mvcReviewer.perform(requestBuilder)
            .andExpect(status().isOk());
    }

    // 5. As a reviewer
    //    I can post a review by providing my reviewer ID, a movie ID and my review text. (Review ID should be autogenerated)
    //    So that I can share my opinions with others.

    @Test
    public void postReviewe() throws Exception {
        Movies movie1 = new Movies(1,"John Wick 4", 2023, "Thriller", 176);
        Reviewer reviewer1 = new Reviewer("Hunaid",LocalDate.now(), 1);

        when(movieRepo.getMovie(movie1.getId())).thenReturn(Optional.of(movie1));
        when(reviewerRepo.findById(reviewer1.getReviewerId())).thenReturn(Optional.of(reviewer1));

        Review review1 = new Review(movie1, "Awesome movie!!", LocalDate.now());

        ReviewRequest request = new ReviewRequest(movie1.getId(), review1.getReviewId(), reviewer1.getReviewerId(), review1.getReview_text());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reviews/save")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(reviewRequest.write(request).getJson());
            
        mvcReview.perform(requestBuilder)
            .andExpect(status().isOk());
    }


    // 6. As a reviewer
    //    I can delete a review by providing my reviewer ID and a review ID
    //    So that I can remove reviews I no longer wish to share.

    @Test
    public void deleteRevieweTest() throws Exception {
        Movies movie1 = new Movies(1,"John Wick 4", 2023, "Thriller", 176);
        Reviewer reviewer1 = new Reviewer("Hunaid",LocalDate.now(), 1);
        Review review1 = new Review(movie1, "nice movies", LocalDate.now());	
        
        when(reviewerRepo.findById(reviewer1.getReviewerId())).thenReturn(Optional.of(reviewer1));
        when(reviewRepo.findById(review1.getReviewId())).thenReturn(Optional.of(review1));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reviewers/1/reviews/1")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON);
            
        mvcReviewer.perform(requestBuilder)
            .andExpect(status().isOk());

        verify(reviewRepo, times(1)).deleteById(1);
    }

    // 7. As a reviewer
    //    I can update a review by providing my reviewer ID, a movie ID and my review text.
    //    So that I can modify the opinion I'm sharing with others.

    @Test
    public void UpdateReview() throws Exception {
        Movies movie1 = new Movies(1,"John Wick 4", 2023, "Thriller", 176);
        Reviewer reviewer1 = new Reviewer(1, "Hunaid", LocalDate.now(), 1);
        Review review1 = new Review(movie1, reviewer1, "This is nice movie");
        
        ReviewRequest request = new ReviewRequest(1, review1.getReviewId(), 1 , "updated review");

        when(reviewRepo.findById(1)).thenReturn(Optional.of(review1));

        MockHttpServletRequestBuilder result = MockMvcRequestBuilders.put("/reviews/1")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(reviewRequest.write(request).getJson());

        mvcReview.perform(result)
            .andExpect(status().isOk())
            .andExpect(content().json(reviewJson.write(review1).getJson()));

        assertEquals(review1.getReview_text(), request.getReviewText());
    }

     // 8. As an Admin
    //    I can add a new movie to the database by providing the data listed in story 1 (Movie ID should be autogenerated)
    //    so that I can share new movies with the users.
    @Test
    public void addMovieByAdmin() throws Exception {

        Movies movie1 = new Movies("The Maula Jutt", 2023, "action", 176);

        when(repoForMovies.save(any(Movies.class))).thenReturn(movie1);

        MockHttpServletRequestBuilder result = MockMvcRequestBuilders.post("/movies/save")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(movie.write(movie1).getJson());

        mvc.perform(result)
            .andExpect(status().isOk())
            .andExpect(content().json(movie.write(movie1).getJson()));  
    }

     // 9. As an Admin
    //    I can add update the entry for a movie by providing the data listed in Story 1.
    //    so that I can correct errors in previously uploaded movie entries.

    @Test
    public void UpdateMovieByAdmin() throws Exception {
        Movies existingMovie = new Movies(1,"John Wick 4", 2023, "Thriller", 176);
        Movies updatedMovie = new Movies(1,"The Maula Jutt", 2023, "action", 176);

        when(repoForMovies.findById(1)).thenReturn(Optional.of(existingMovie));
        when(repoForMovies.save(any(Movies.class))).thenReturn(updatedMovie);

        MockHttpServletRequestBuilder result = MockMvcRequestBuilders.put("/movies/1")
            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .content(movie.write(updatedMovie).getJson());

        mvc.perform(result)
            .andExpect(status().isOk())
            .andExpect(content().json(movie.write(updatedMovie).getJson()));

        verify(repoForMovies, times(1)).findById(1);
    }


    //10. As an admin
    //    I can delete a movie by providing a movie ID
    //    so that I can remove movies I no longer wish to share.

    @Test
public void deleteMovieByAdmin() throws Exception {
    Movies existingMovie = new Movies(1, "John Wick 4", 2023, "Thriller", 176);

    when(repoForMovies.findById(1)).thenReturn(Optional.of(existingMovie));

    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/movies/1");

    mvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().string("Movie is deleted"));
}







}

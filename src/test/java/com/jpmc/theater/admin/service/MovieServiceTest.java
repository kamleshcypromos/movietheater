package com.jpmc.theater.admin.service;

import com.jpmc.theater.model.Movie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieService = new MovieService();
    }

    @AfterEach
    void tearDown() {
        movieService = null;
    }

    @Test
    void getMovies() {
        List<Movie> movieList = movieService.getMovies();
        assertEquals(3, movieList.size());

    }
}
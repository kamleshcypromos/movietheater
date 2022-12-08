package com.jpmc.theater.admin.service;

import com.google.gson.*;
import com.jpmc.theater.model.Movie;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class MovieService implements IMovieService {

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Duration.class, (JsonDeserializer<Duration>) (json, type, jsonDeserializationContext)
                    -> Duration.ofMinutes(json.getAsJsonPrimitive().getAsLong()))
            .registerTypeAdapter(Duration.class, (JsonSerializer<Duration>) (duration, type, jsonSerializationContext)
                    -> new JsonPrimitive(duration.toMinutes()))
            .create();

    private List<Movie> movies = new ArrayList<>();

    public MovieService() {
        init();
    }

    private void init() {
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/movies.json");
        Movie[] movieArray = GSON.fromJson(new InputStreamReader(resourceAsStream), Movie[].class);
        movies.addAll(Arrays.asList(movieArray));

    }

    @Override
    public List<Movie> getMovies() {
        return movies;
    }

   /* @Override
    public void addMovie(Movie movie) {
        movies.add(movie);
        updateMovie();
    }

    @Override
    public void deleteMovie(Movie movie) {
        movies.remove(movie);
        updateMovie();
    }

    private void updateMovie() {
        File movieFileJson = new File(this.getClass().getResource("/movies.json").getFile());
        try {
            FileWriter fileWriter = new FileWriter(movieFileJson.getAbsolutePath());
            GSON.toJson(movies, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            log.error("Unable to add movie ", e);
            throw new RuntimeException(e);
        }
    }*/
}

package com.jpmc.theater.admin.service;

import com.google.gson.*;
import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Log4j2
public class SchedulerService implements ISchedulerService{

    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(LocalDate.now() + " " + json.getAsJsonPrimitive().getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (duration, type, jsonSerializationContext)
                    -> new JsonPrimitive("T" + duration.getHour() + ":" + duration.getMinute()))
            .create();

    public static final Gson GSON_LOGGER = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Movie.class, (JsonSerializer<Movie>) (movie, type, jsonDeserializationContext)
                    -> getMovieJsonPrimitive(movie))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (dateTime, type, jsonDeserializationContext)
                    -> getLocalDateTimePrimitive(dateTime))
            .create();

    private static JsonElement getLocalDateTimePrimitive(LocalDateTime dateTime) {
        return new JsonPrimitive(dateTime.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    private static JsonElement getMovieJsonPrimitive(Movie movie) {
        StringBuilder jsonStringBuilder = new StringBuilder();
        String movieString = jsonStringBuilder.append("{")
                .append("'title':").append("'").append(movie.getTitle()).append("'").append(",")
                .append("'duration':").append("'").append(movie.getHumanReadableRunTime()).append("'").append(",")
                .append("'ticketPrice': '$").append(movie.getTicketPrice()).append("'").append("}").toString();
        JsonElement jsonElement = JsonParser.parseString(movieString);
        return jsonElement;
    }

    private List<Showing> schedules = new ArrayList<>();
    IMovieService movieService;

    public SchedulerService(IMovieService movieService){
        this.movieService = movieService;
        initSchedules();
    }

    private void initSchedules(){
        List<Movie> movies = movieService.getMovies();
        movies.sort(Comparator.comparing(Movie::getId));
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/schedule.json");
        Showing[] showings = GSON.fromJson(new InputStreamReader(resourceAsStream), Showing[].class);
        List<Showing> showingList = Arrays.asList(showings);
        showingList.forEach(showing -> {
            showing.setMovie(movies.stream().filter(movie -> movie.getId() == showing.getMovieId()).findFirst().get());
        });
        schedules.addAll(showingList);
    }

    @Override
    public List<Showing> getSchedules() {
        return schedules;
    }

    @Override
    public void printSchedules() {
        log.info(LocalDate.now());
        log.info("===================================================");
        getSchedules().forEach(showing -> {
            log.info(showing.toString());
        });
        log.info("===================================================");

        log.info("=================== JSON FORMAT ================================");
        schedules.forEach(showing -> {
            log.info(GSON_LOGGER.toJson(showing));
        });
        log.info("=================== JSON FORMAT ================================");
    }
}

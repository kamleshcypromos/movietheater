package com.jpmc.theater.client.service;

import com.jpmc.theater.admin.service.ISchedulerService;
import com.jpmc.theater.admin.service.MovieService;
import com.jpmc.theater.admin.service.SchedulerService;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    IReservationService reservationService;
    ISchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        MovieService movieService = new MovieService();
        schedulerService = new SchedulerService(movieService);
        reservationService = new ReservationService();
    }

    @AfterEach
    void tearDown() {
        reservationService = null;
        schedulerService = null;
    }

    @Test
    void reserveWithSequenceDiscount() {
        Movie movie = Movie.builder()
                .id(1)
                .specialCode(1)
                .title("Spider-Man: No Way Home")
                .runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5d)
                .build();
        Showing showing = Showing.builder()
                .showStartTime(LocalDateTime.now())
                .sequenceOfTheDay(1)
                .movie(movie).build();
        schedulerService.getSchedules().stream()
                .filter(s -> s.getSequenceOfTheDay() == 1 && s.getMovieId() == 2).findFirst().orElse(null);
        Reservation reservation = reservationService
                .reserve(Customer.builder().name("John Doe").id("123").build(), showing, 3);
        assertEquals(28.5, reservation.getTotalFee());
    }

    @Test
    void reserveWithSpecialDiscount() {
        Movie movie = Movie.builder()
                .id(1)
                .specialCode(1)
                .title("Spider-Man: No Way Home")
                .runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5d)
                .build();
        Showing showing = Showing.builder()
                .showStartTime(LocalDateTime.now())
                .sequenceOfTheDay(3)
                .movie(movie).build();
        schedulerService.getSchedules().stream()
                .filter(s -> s.getSequenceOfTheDay() == 1 && s.getMovieId() == 2).findFirst().orElse(null);
        Reservation reservation = reservationService
                .reserve(Customer.builder().name("John Doe").id("123").build(), showing, 3);
        assertEquals(30.0, reservation.getTotalFee());
    }

    @Test
    void reserveWithShowStartTimeAfter11AM(){
        Movie movie = Movie.builder()
                .id(1)
                .title("Spider-Man: No Way Home")
                .runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5d)
                .build();
        Showing showing = Showing.builder()
                .showStartTime(LocalDateTime.parse(LocalDate.now() + " 11:10", ReservationService.YYYYMMDDDHHMM))
                .sequenceOfTheDay(3)
                .movie(movie).build();
        schedulerService.getSchedules().stream()
                .filter(s -> s.getSequenceOfTheDay() == 1 && s.getMovieId() == 2).findFirst().orElse(null);
        Reservation reservation = reservationService
                .reserve(Customer.builder().name("John Doe").id("123").build(), showing, 3);
        assertEquals(28.125, reservation.getTotalFee());
    }

    @Test
    void reserveWithShowStartTimeAfter4PM(){
        Movie movie = Movie.builder()
                .id(1)
                .title("Spider-Man: No Way Home")
                .runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5d)
                .build();
        Showing showing = Showing.builder()
                .showStartTime(LocalDateTime.parse(LocalDate.now() + " 16:01", ReservationService.YYYYMMDDDHHMM))
                .sequenceOfTheDay(3)
                .movie(movie).build();
        schedulerService.getSchedules().stream()
                .filter(s -> s.getSequenceOfTheDay() == 1 && s.getMovieId() == 2).findFirst().orElse(null);
        Reservation reservation = reservationService
                .reserve(Customer.builder().name("John Doe").id("123").build(), showing, 3);
        assertEquals(37.5, reservation.getTotalFee());
    }

    @Test
    void reserveWithShowStartDateis7(){
        Movie movie = Movie.builder()
                .id(1)
                .title("Spider-Man: No Way Home")
                .runningTime(Duration.ofMinutes(90))
                .ticketPrice(12.5d)
                .build();
        Showing showing = Showing.builder()
                .showStartTime(LocalDateTime.parse(  "2022-12-07 16:00", ReservationService.YYYYMMDDDHHMM))
                .sequenceOfTheDay(3)
                .movie(movie).build();
        schedulerService.getSchedules().stream()
                .filter(s -> s.getSequenceOfTheDay() == 1 && s.getMovieId() == 2).findFirst().orElse(null);
        Reservation reservation = reservationService
                .reserve(Customer.builder().name("John Doe").id("123").build(), showing, 3);
        assertEquals(34.5, reservation.getTotalFee());
    }

}
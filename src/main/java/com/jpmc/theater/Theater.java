package com.jpmc.theater;

import com.jpmc.theater.admin.service.IMovieService;
import com.jpmc.theater.admin.service.ISchedulerService;
import com.jpmc.theater.admin.service.MovieService;
import com.jpmc.theater.admin.service.SchedulerService;
import com.jpmc.theater.client.service.IReservationService;
import com.jpmc.theater.client.service.ReservationService;
import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
public class Theater {

    private IMovieService movieService;
    private ISchedulerService schedulerService;
    private IReservationService reservationService;

    public Theater() {
        movieService = new MovieService();
        schedulerService = new SchedulerService(movieService);
        reservationService = new ReservationService();
    }

    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing = schedulerService.getSchedules().stream().filter(s -> s.getSequenceOfTheDay() == sequence).findFirst()
                .orElseThrow(()-> {throw new RuntimeException("No showings found for the given sequence");});
       log.info("Reserving Movie = {} for Customer = {} , ", showing.getMovie(), customer);
        return reservationService.reserve(customer, showing, howManyTickets);
    }


    public static void main(String[] args) {
        Theater theater = new Theater();
        theater.printSchedules();
    }

    private void printSchedules() {
        schedulerService.printSchedules();
    }


}

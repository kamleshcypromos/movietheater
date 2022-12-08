package com.jpmc.theater.client.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReservationService implements IReservationService{

    public static final DateTimeFormatter YYYYMMDDDHHMM
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private Map<Integer, Double> SEQUENCE_OF_DAY_DISCOUNTS =
            Map.of(1,3d,2,2d);

    @Override
    public Reservation reserve(Customer customer, Showing showing, int howManyTickets) {
        double pricePerTicket =
                showing.getMovie().getTicketPrice() - getDiscount(showing);
        return Reservation.builder()
                .customer(customer)
                .audienceCount(howManyTickets)
                .totalFee(howManyTickets * pricePerTicket)
                .build();
    }


    private double getDiscount(Showing showing) {
        List<Double> availableDiscounts =  new ArrayList<>();
        double ticketPrice = showing.getMovie().getTicketPrice();
        LocalDateTime showStartTime = showing.getShowStartTime();
        LocalDateTime elevenAmTime = LocalDateTime.parse(LocalDate.now() + " " + "11:00", YYYYMMDDDHHMM);
        LocalDateTime fourPMTime = LocalDateTime.parse(LocalDate.now() + " " + "16:00", YYYYMMDDDHHMM);
        boolean isShowStartTimeBetween11AMAnd4PM = showStartTime.isAfter(elevenAmTime)
                && showStartTime.isBefore(fourPMTime);
        boolean isShowingOnSeventh = showStartTime.getDayOfMonth() == 7;
        if(isShowingOnSeventh){
            availableDiscounts.add(1d);
        }
        if(isShowStartTimeBetween11AMAnd4PM){
            // 25% discount for special movie
            availableDiscounts.add(ticketPrice * 0.25);
        }
        if (showing.getMovie().isSpecial()) {
            // 20% discount for special movie
            availableDiscounts.add(ticketPrice * 0.2);
        }
        int showSequence = showing.getSequenceOfTheDay();
        availableDiscounts.add(SEQUENCE_OF_DAY_DISCOUNTS.getOrDefault(showSequence, 0d));
        // biggest discount wins
        return Collections.max(availableDiscounts);
    }


}

package com.jpmc.theater.client.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Reservation;
import com.jpmc.theater.model.Showing;

public interface IReservationService {
    Reservation reserve(Customer customer, Showing showing, int howManyTickets);
}

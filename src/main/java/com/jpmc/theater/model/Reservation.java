package com.jpmc.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    private Customer customer;
    private Showing showing;
    //private Theater theater;
    private int audienceCount;
    private double totalFee;

}

package com.jpmc.theater.model;

import lombok.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Movie {
    public static final int MOVIE_CODE_SPECIAL = 1;
    private int id;
    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public boolean isSpecial(){
        return MOVIE_CODE_SPECIAL == getSpecialCode();
    }

    public String getHumanReadableRunTime(){
        long hour = runningTime.toHours();
        long remainingMin = runningTime.toMinutes() - TimeUnit.HOURS.toMinutes(runningTime.toHours());
        return String.format("(%s hour%s %s minute%s)", hour, hour > 1 ? "s" : "",
                remainingMin, remainingMin > 1 ? "s" : "");
    }

}
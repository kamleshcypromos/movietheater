package com.jpmc.theater.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Showing {
    private Movie movie;
    private int movieId;
    private int sequenceOfTheDay;
    private LocalDateTime showStartTime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(sequenceOfTheDay).append(':').append(showStartTime)
          .append(" ")
          .append(movie.getTitle())
          .append(" ")
          .append(movie.getHumanReadableRunTime())
          .append(" $").append(movie.getTicketPrice())
        ;
        return sb.toString();
    }
}

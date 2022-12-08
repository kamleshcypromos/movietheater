package com.jpmc.theater.model;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Customer {

    private String name;
    private String id;


}
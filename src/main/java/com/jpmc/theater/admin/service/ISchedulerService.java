package com.jpmc.theater.admin.service;

import com.jpmc.theater.model.Showing;

import java.util.List;

public interface ISchedulerService {

    List<Showing> getSchedules();

    void printSchedules();




}

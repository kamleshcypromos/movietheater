package com.jpmc.theater.admin.service;

import com.jpmc.theater.model.Showing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerServiceTest {

    private ISchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        MovieService movieService = new MovieService();
        schedulerService = new SchedulerService(movieService);
    }

    @AfterEach
    void tearDown() {
        schedulerService = null;
    }

    @Test
    void getSchedules() {
        List<Showing> showingList = schedulerService.getSchedules();
        assertEquals(9, showingList.size());
    }
}
package com.utusikov.hw3;

import com.utusikov.hw3.controller.AdminController;
import com.utusikov.hw3.controller.StatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StatTest {
    @Autowired
    AdminController admin;
    @Autowired
    StatController controller;

    @Test
    public void testAvg() {
        assertNotNull(controller.doStatPerDay());
        assertNotNull(controller.doAvgStat());
    }
}

package com.utusikov.hw3;

import com.utusikov.hw3.controller.AdminController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class AdminTest {
    @Autowired
    private AdminController controller;

    @Test
    public void testCreateAccount() {
        controller.doCreateAcc();
        controller.doCreateAcc();
        controller.doCreateAcc();
        assertTrue(controller.doCheckAcc(1L));
        assertTrue(controller.doCheckAcc(2L));
        assertTrue(controller.doCheckAcc(3L));
        assertFalse(controller.doCheckAcc(4L));
    }

    @Test
    public void testAddVisits() {
        controller.doCreateAcc();
        controller.doAddVisits(1L, 10L);
        assertEquals(10L, controller.doGetAccActualVisits(1L).longValue());
        assertEquals(0L, controller.doGetAccDoneVisits(1L).longValue());
    }
}

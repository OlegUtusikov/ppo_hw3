package com.utusikov.hw3;

import com.utusikov.hw3.controller.AdminController;
import com.utusikov.hw3.controller.TurnstileController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class TurnstileTest {
    @Autowired
    AdminController admin;
    @Autowired
    TurnstileController controller;

    @Test
    public void testCreateAccount() {
        admin.doCreateAcc();
        admin.doCreateAcc();
        admin.doAddVisits(1L, 10L);
        admin.doAddVisits(2L, 1L);
        assertTrue(controller.doEnter(1L));
        assertFalse(controller.doEnter(1L));
        assertFalse(controller.doLeave(2L));
        assertTrue(controller.doLeave(1L));
        assertTrue(controller.doEnter(2L));
        assertTrue(controller.doLeave(2L));
        assertFalse(controller.doEnter(2L));
    }
}

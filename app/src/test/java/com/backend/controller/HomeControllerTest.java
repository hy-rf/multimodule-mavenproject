package com.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class HomeControllerTest {

    private HomeController homeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        homeController = new HomeController();

        // Manually inject @Value property
        injectTitle(homeController, "Test Home Title");

        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Test
    void testHomeEndpoint() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(content().string("Test Home Title"));
    }

    @Test
    void testIndexRedirectsToSwaggerUI() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/swagger-ui.html"));
    }

    // Helper method to inject private field manually (bypass Spring @Value)
    private void injectTitle(HomeController controller, String value) {
        try {
            var field = HomeController.class.getDeclaredField("title");
            field.setAccessible(true);
            field.set(controller, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
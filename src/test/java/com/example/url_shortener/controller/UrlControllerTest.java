package com.example.url_shortener.controller;

import com.example.url_shortener.UrlMapping;
import com.example.url_shortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UrlControllerTest {

    private MockMvc mockMvc;

    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = mock(UrlService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UrlController(urlService)).build();
    }

    @Test
    void shortenUrl_WithAlias_ReturnsShortUrl() throws Exception {
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode("myalias");

        when(urlService.shortenUrl("https://example.com", "myalias")).thenReturn(mapping);

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"https://example.com\",\"alias\":\"myalias\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").value("http://localhost:8080/u/myalias"));

        verify(urlService).shortenUrl(eq("https://example.com"), eq("myalias"));
    }

    @Test
    void shortenUrl_WithDuplicateAlias_ReturnsBadRequest() throws Exception {
        when(urlService.shortenUrl("https://example.com", "taken"))
                .thenThrow(new IllegalArgumentException("Alias already in use"));

        mockMvc.perform(post("/api/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"https://example.com\",\"alias\":\"taken\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Alias already in use"));
    }
}

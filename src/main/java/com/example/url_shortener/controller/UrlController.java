package com.example.url_shortener.controller;

import com.example.url_shortener.UrlMapping;
import com.example.url_shortener.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@RestController
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * API to shorten a URL
     */
    @PostMapping("/api/shorten")
    public ResponseEntity<?> shortenUrl(@RequestBody Map<String, String> request) {

        String originalUrl = request.get("url");
        if (originalUrl == null || originalUrl.isBlank()) {
            return ResponseEntity.badRequest().body("URL is required");
        }

        UrlMapping mapping = urlService.shortenUrl(originalUrl);

        String shortUrl = "http://localhost:8080/u/" + mapping.getShortCode();

        return ResponseEntity.ok(Map.of("shortUrl", shortUrl));
    }

    /**
     * Redirect short URL to original URL
     */
    @GetMapping("/u/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        Optional<UrlMapping> mappingOpt =
                urlService.getAndUpdateClickCount(shortCode);

        if (mappingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UrlMapping mapping = mappingOpt.get();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(mapping.getOriginalUrl()));

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/api/analytics/{shortCode}")
    public ResponseEntity<?> getAnalytics(@PathVariable String shortCode) {

        return urlService.getAnalytics(shortCode)
                .map(mapping -> ResponseEntity.ok(
                        new com.example.url_shortener.dto.UrlAnalyticsResponse(
                                mapping.getShortCode(),
                                mapping.getOriginalUrl(),
                                mapping.getClickCount(),
                                mapping.getCreatedAt()
                        )
                ))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}

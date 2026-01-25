package com.example.url_shortener.dto;

import java.time.LocalDateTime;

public class UrlAnalyticsResponse {

    private String shortCode;
    private String originalUrl;
    private Long clickCount;
    private LocalDateTime createdAt;

    public UrlAnalyticsResponse(
            String shortCode,
            String originalUrl,
            Long clickCount,
            LocalDateTime createdAt
    ) {
        this.shortCode = shortCode;
        this.originalUrl = originalUrl;
        this.clickCount = clickCount;
        this.createdAt = createdAt;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

package com.example.url_shortener.service;

import com.example.url_shortener.UrlMapping;
import com.example.url_shortener.exception.AliasAlreadyExistsException;
import com.example.url_shortener.repository.UrlRepository;
import com.example.url_shortener.util.Base62Encoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {

    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Create a short URL for a given original URL
     */
    public UrlMapping shortenUrl(String originalUrl, String alias) {

        // =========================================
        // CUSTOM ALIAS PROVIDED
        // =========================================
        if (alias != null && !alias.isBlank()) {

            alias = alias.trim();

            // Check only the alias column
            if (urlRepository.existsByAlias(alias)) {
                throw new AliasAlreadyExistsException(
                        "Alias already exists"
                );
            }

            // Create custom alias mapping
            UrlMapping mapping = new UrlMapping();

            mapping.setOriginalUrl(originalUrl);
            mapping.setAlias(alias);
            mapping.setShortCode("~" + alias);

            return urlRepository.save(mapping);
        }

        // =========================================
        // NO CUSTOM ALIAS
        // =========================================

        // Always create a new Base62 short link
        UrlMapping mapping = new UrlMapping();

        mapping.setOriginalUrl(originalUrl);

        // Save first to generate database ID
        UrlMapping saved = urlRepository.save(mapping);

        // Generate Base62 short code
        String shortCode =
                Base62Encoder.encode(saved.getId());

        saved.setShortCode(shortCode);

        return urlRepository.save(saved);
    }

    /**
     * Fetch original URL using short code
     */
    public Optional<UrlMapping> getAndUpdateClickCount(
            String shortCode) {

        Optional<UrlMapping> mappingOpt =
                urlRepository.findByShortCode(shortCode);

        mappingOpt.ifPresent(mapping -> {

            mapping.setClickCount(
                    mapping.getClickCount() + 1
            );

            urlRepository.save(mapping);
        });

        return mappingOpt;
    }

    /**
     * Get analytics for a short URL
     */
    public Optional<UrlMapping> getAnalytics(
            String shortCode) {

        return urlRepository.findByShortCode(shortCode);
    }
}
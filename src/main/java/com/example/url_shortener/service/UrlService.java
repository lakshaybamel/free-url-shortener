package com.example.url_shortener.service;

import com.example.url_shortener.UrlMapping;
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
    public UrlMapping shortenUrl(String originalUrl) {

        // 1. Check if URL already exists
        Optional<UrlMapping> existing =
                urlRepository.findByOriginalUrl(originalUrl);

        if (existing.isPresent()) {
            return existing.get();  // return old short URL
        }

        // 2. Create new entry
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);

        UrlMapping saved = urlRepository.save(mapping);

        // 3. Generate short code
        String shortCode = Base62Encoder.encode(saved.getId());
        saved.setShortCode(shortCode);

        return urlRepository.save(saved);
    }


    /**
     * Fetch original URL using short code
     */
    public Optional<UrlMapping> getAndUpdateClickCount(String shortCode) {

        Optional<UrlMapping> mappingOpt = urlRepository.findByShortCode(shortCode);

        mappingOpt.ifPresent(mapping -> {
            mapping.setClickCount(mapping.getClickCount() + 1);
            urlRepository.save(mapping);
        });

        return mappingOpt;
    }

    public Optional<UrlMapping> getAnalytics(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

}

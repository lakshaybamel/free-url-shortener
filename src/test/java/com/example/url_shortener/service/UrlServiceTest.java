package com.example.url_shortener.service;

import com.example.url_shortener.UrlMapping;
import com.example.url_shortener.repository.UrlRepository;
import com.example.url_shortener.util.Base62Encoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlService urlService;

    @Test
    void shortenUrl_WithCustomAlias_UsesProvidedAlias() {
        String url = "https://example.com/very/long/path";
        String alias = "custom123";

        when(urlRepository.existsByShortCode(alias)).thenReturn(false);
        when(urlRepository.save(any(UrlMapping.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UrlMapping result = urlService.shortenUrl(url, alias);

        assertEquals(alias, result.getShortCode());
        assertEquals(url, result.getOriginalUrl());
        verify(urlRepository, never()).findByOriginalUrl(anyString());
    }

    @Test
    void shortenUrl_WithDuplicateCustomAlias_ThrowsError() {
        when(urlRepository.existsByShortCode("taken")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> urlService.shortenUrl("https://example.com", "taken"));

        assertEquals("Alias already in use", exception.getMessage());
        verify(urlRepository, never()).save(any(UrlMapping.class));
    }

    @Test
    void shortenUrl_WithoutAlias_UsesExistingFlow() {
        String url = "https://example.com";

        UrlMapping firstSave = new UrlMapping();
        firstSave.setOriginalUrl(url);
        firstSave.setShortCode(null);
        ReflectionTestUtils.setField(firstSave, "id", 37L);

        String expectedCode = Base62Encoder.encode(37L);

        UrlMapping secondSave = new UrlMapping();
        secondSave.setOriginalUrl(url);
        secondSave.setShortCode(expectedCode);

        when(urlRepository.findByOriginalUrl(url)).thenReturn(Optional.empty());
        when(urlRepository.save(any(UrlMapping.class))).thenReturn(firstSave, secondSave);

        UrlMapping result = urlService.shortenUrl(url, null);

        assertEquals(expectedCode, result.getShortCode());
        ArgumentCaptor<UrlMapping> captor = ArgumentCaptor.forClass(UrlMapping.class);
        verify(urlRepository, times(2)).save(captor.capture());
        assertNull(captor.getAllValues().get(0).getShortCode());
        assertEquals(expectedCode, captor.getAllValues().get(1).getShortCode());
    }
}

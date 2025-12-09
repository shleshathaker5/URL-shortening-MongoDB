
package urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortner.model.ShortenUrlRequest;
import urlshortner.model.ShortenUrlResponse;

import urlshortner.service.UrlShortenerService;

import java.net.URI;
import java.util.Map;


@RestController
public class UrlController {

    private final UrlShortenerService service;

    public UrlController(UrlShortenerService service) {
        this.service = service;
    }


    // shorten url
    @PostMapping("/shorten")
    public ResponseEntity<?> shorten(@RequestBody ShortenUrlRequest req) {
        if (req == null || req.getUrl() == null || req.getUrl().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "url is required"));
        }
        ShortenUrlResponse res = service.shorten(req);
        String shortUrl = String.format("%s/%s", baseUrl(), res.getCode());
        return ResponseEntity.ok(Map.of("shortCode", res.getCode(), "shortUrl", shortUrl));
    }

    // redirect to  url
    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@PathVariable String code) {
        String longUrl = service.expand(code);
        if (longUrl == null) return ResponseEntity.notFound().build();
        return ResponseEntity.status(302).location(URI.create(longUrl)).build();
    }

    private String baseUrl() {
        // In production use proper config or request-scheme/host. For simplicity:
        return "http://localhost:8080";
    }
}

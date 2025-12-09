package urlshortner.service;


import urlshortner.model.ShortenUrlRequest;
import urlshortner.model.ShortenUrlResponse;

public interface UrlShortenerService {
    ShortenUrlResponse shorten(ShortenUrlRequest req);
    String expand(String code);
}
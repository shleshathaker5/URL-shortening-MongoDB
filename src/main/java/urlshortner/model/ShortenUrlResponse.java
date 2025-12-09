package urlshortner.model;

public class ShortenUrlResponse {
    private String shortUrl;
    private String code;

    public ShortenUrlResponse() {}
    public ShortenUrlResponse(String shortUrl, String code) {
        this.shortUrl = shortUrl;
        this.code = code;
    }

    public String getCode() { return code; }
}

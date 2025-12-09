package urlshortner.model;

import jakarta.validation.constraints.NotBlank;

public class ShortenUrlRequest {

    @NotBlank
    //@Pattern(regexp = "https?://.*") // optional stricter validation
    private String url;


    public ShortenUrlRequest() {}

    public ShortenUrlRequest(String url) { this.url = url; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url;}
}

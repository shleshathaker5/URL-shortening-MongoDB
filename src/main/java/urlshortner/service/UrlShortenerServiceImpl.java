package urlshortner.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import urlshortner.model.ShortenUrlRequest;
import urlshortner.model.ShortenUrlResponse;
import urlshortner.persistance.UrlMapping;
import urlshortner.repository.UrlMappingRepository;
import urlshortner.util.Base62;

import java.time.Instant;

@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

        private final UrlMappingRepository repo;
        private static final int OBJECT_ID_BYTES = 12;
        @Value("${app.base-url:http://localhost:8080}")
        private String baseUrl;

        public UrlShortenerServiceImpl(UrlMappingRepository repo) {
            this.repo = repo;
        }

        // Shorten: create new ObjectId, encode it, store mapping
        public ShortenUrlResponse shorten(ShortenUrlRequest req) {
            // create new ObjectId on client side so we know the id before saving
            ObjectId oid = new ObjectId();
            String idHex = oid.toHexString();
            byte[] oidBytes = oid.toByteArray();

            String code = Base62.encode(oidBytes);

            UrlMapping mapping = new UrlMapping();
            mapping.setId(idHex);
            mapping.setLongUrl(req.getUrl());
            mapping.setShortCode(code);
            mapping.setCreatedAt(Instant.now().toEpochMilli());

            repo.save(mapping);
            return new ShortenUrlResponse(baseUrl + "/" + code, code);
        }

        // Expand: decode code -> bytes -> ObjectId -> lookup
        public String expand(String code) {
            if (code == null || code.isEmpty()) return null;

            // Try direct lookup first (faster if stored)
            UrlMapping m = repo.findByShortCode(code);
            if (m != null) return m.getLongUrl();

            // Otherwise decode back to ObjectId
            try {
                byte[] oidBytes = Base62.decodeToBytes(code, OBJECT_ID_BYTES);
                ObjectId oid = new ObjectId(oidBytes);
                return repo.findById(oid.toHexString()).map(UrlMapping::getLongUrl).orElse(null);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
    }


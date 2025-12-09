package urlshortner.repository;



import org.springframework.data.mongodb.repository.MongoRepository;
import urlshortner.persistance.UrlMapping;



public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    UrlMapping findByShortCode(String shortCode);
}

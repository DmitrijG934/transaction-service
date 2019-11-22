package nn.dgord.cachedapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CachedAppApplication {
    public static final String API_PREFIX = "api/v1/";
    public static final String TRANSACTION_OWNER_CACHE_NAME = "transaction_owner";
    public static final String DEPARTMENT_CACHE_NAME = "departments";

    public static void main(String[] args) {
        SpringApplication.run(CachedAppApplication.class, args);
    }
}

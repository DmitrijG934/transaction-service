package nn.dgord.cachedapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static nn.dgord.cachedapp.CachedAppApplication.DEPARTMENT_CACHE_NAME;
import static nn.dgord.cachedapp.CachedAppApplication.TRANSACTION_OWNER_CACHE_NAME;

@Slf4j
@Configuration
public class ApplicationConfig {
    @Bean
    public CacheManager simpleCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
                List.of(
                        new ConcurrentMapCache(TRANSACTION_OWNER_CACHE_NAME),
                        new ConcurrentMapCache(DEPARTMENT_CACHE_NAME)
                )
        );
        log.info("Initialized cache manager.");
        return cacheManager;
    }
}

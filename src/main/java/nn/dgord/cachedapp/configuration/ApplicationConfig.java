package nn.dgord.cachedapp.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class ApplicationConfig {
    @Bean
    public CacheManager ehCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(
                Collections.singleton(new ConcurrentMapCache("transaction_owners"))
        );
        return cacheManager;
    }
}

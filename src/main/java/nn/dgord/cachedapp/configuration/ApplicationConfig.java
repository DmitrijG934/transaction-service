package nn.dgord.cachedapp.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("nn.dgord.cachedapp.controller.rest"))
                .paths(PathSelectors.any())
                .build().apiInfo(ApiInfo.DEFAULT);
    }

}

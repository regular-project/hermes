package org.hermes.core.config;


import org.hermes.core.cache.CombinedCache;
import org.hermes.core.cache.CombinedCacheFactory;
import org.hermes.core.cache.DefaultCache;
import org.hermes.core.cache.DefaultCacheFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class CacheConfig {

    @Value("${hermes.cache.redis.host}")
    private String redisHost;

    @Value("${hermes.cache.redis.port}")
    private Integer port;

    @Value("${hermes.cache.valid-interval}")
    private Long cacheValidInterval;

    @Bean
    public Jedis jedis() {
        return new Jedis(redisHost, port);
    }

    @Bean
    public CombinedCache combinedCache() {
        return CombinedCacheFactory.getRedisBasedCombinedCache(cacheValidInterval, jedis());
    }

    @Bean
    public DefaultCache defaultCache() {
        return DefaultCacheFactory.getRedisBasedDefaultCache(cacheValidInterval, jedis());
    }
}

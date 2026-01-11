package com.flowrunner.config;

import com.flowrunner.core.cache.InMemoryProcessCache;
import com.flowrunner.core.cache.ProcessCache;
import com.flowrunner.core.cache.RedisProcessCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = { "com.example", "com.flowrunner" })
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${cache.type:default}")
    private String cacheType;

    @Value("${redis.host:localhost}")
    private String redisHost;

    @Value("${redis.port:6379}")
    private int redisPort;

    @Bean
    public ProcessCache processCache() {
        if ("redis".equalsIgnoreCase(cacheType)) {
            System.out.println("[Config] Initializing RedisProcessCache (" + redisHost + ":" + redisPort + ")");
            return new RedisProcessCache(redisHost, redisPort);
        } else {
            System.out.println("[Config] Initializing InMemoryProcessCache");
            return new InMemoryProcessCache();
        }
    }
}

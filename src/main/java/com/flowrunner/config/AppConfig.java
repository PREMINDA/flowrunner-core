package com.flowrunner.config;

import com.flowrunner.core.cache.InMemoryProcessCache;
import com.flowrunner.core.cache.IProcessCache;
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
    @Bean
    public IProcessCache processCache() {
            System.out.println("[Config] Initializing InMemoryProcessCache");
            return new InMemoryProcessCache();
    }
}

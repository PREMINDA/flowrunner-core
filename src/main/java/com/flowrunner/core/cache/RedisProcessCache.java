package com.flowrunner.core.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowrunner.core.model.ProcessNode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

public class RedisProcessCache implements ProcessCache {

    private final JedisPool jedisPool;
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisProcessCache(String host, int port) {
        this.jedisPool = new JedisPool(host, port);
        // Configure mapper if needed, similar to JsonLoader (ignore unknown props
        // inside objects if they change)
        // But for storage/retrieval exact match is usually fine.
    }

    @Override
    public Optional<ProcessNode> getProcess(String id) {
        try (Jedis jedis = jedisPool.getResource()) {
            String json = jedis.get("process:" + id);
            if (json != null) {
                try {
                    ProcessNode process = mapper.readValue(json, ProcessNode.class);
                    return Optional.of(process);
                } catch (JsonProcessingException e) {
                    System.err.println("[Redis] Failed to parse JSON for " + id);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("[Redis] Error connecting/getting: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void cacheProcess(ProcessNode process) {
        if (process == null || process.getId() == null)
            return;

        try (Jedis jedis = jedisPool.getResource()) {
            String json = mapper.writeValueAsString(process);
            jedis.set("process:" + process.getId(), json);
            System.out.println("[Redis] Stored process: " + process.getId());
        } catch (Exception e) {
            System.err.println("[Redis] Error caching: " + e.getMessage());
        }
    }
}

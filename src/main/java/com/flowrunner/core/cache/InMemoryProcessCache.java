package com.flowrunner.core.cache;

import com.flowrunner.core.model.CallProcessNode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Managed manually in AppConfig now
public class InMemoryProcessCache implements ProcessCache {
    private final Map<String, CallProcessNode> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<CallProcessNode> getProcess(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void cacheProcess(CallProcessNode process) {
        if (process != null && process.getId() != null) {
            cache.put(process.getId(), process);
            System.out.println("[Cache] Stored process: " + process.getId());
        }
    }
}

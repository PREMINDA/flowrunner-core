package com.flowrunner.core.cache;

import com.flowrunner.core.model.FlowDef;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Managed manually in AppConfig now
public class InMemoryProcessCache implements IProcessCache {
    private final Map<String, FlowDef> cache = new ConcurrentHashMap<>();

    @Override
    public Optional<FlowDef> getProcess(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public void cacheProcess(FlowDef process) {
        if (process == null || process.getFilename() == null)
            return;
        cache.put(process.getFilename(), process);
        System.out.println("[Cache] Stored process: " + process.getId());
    }
}

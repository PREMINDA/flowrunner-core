package com.flowrunner.core.service;

import com.flowrunner.core.cache.IProcessCache;
import com.flowrunner.core.model.FlowDef;
import com.flowrunner.core.util.JsonLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class FlowService {

    private final IProcessCache processCache;

    @Autowired
    public FlowService(IProcessCache processCache) {
        this.processCache = processCache;
    }

    public FlowDef getProcess(String resourceName) throws IOException {

        FlowDef loaded = JsonLoader.loadProcessFromResource(resourceName);
        String processName = loaded.getFilename();

        Optional<FlowDef> cached = processCache.getProcess(processName);
        if (cached.isPresent()) {
            System.out.println("[Service] Cache HIT for " + processName);
            return cached.get();
        } else {
            System.out.println("[Service] Cache MISS for " + processName + ". Caching now.");
            processCache.cacheProcess(loaded);
            return loaded;
        }
    }
}

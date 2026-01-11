package com.flowrunner.core.service;

import com.flowrunner.core.cache.ProcessCache;
import com.flowrunner.core.model.CallProcessNode;
import com.flowrunner.core.util.JsonLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProcessService {

    private final ProcessCache processCache;

    @Autowired
    public ProcessService(ProcessCache processCache) {
        this.processCache = processCache;
    }

    public CallProcessNode getProcess(String filePath) throws IOException {
        // Limitation: In a real system you'd lookup by ID, not path.
        // For this demo, we assume the ID is inside the file, so we have to load it to
        // check the cache efficiently,
        // OR we just cache by filePath/ID after first load.
        // Let's implement a simple optimize:
        // 1. Load file (this part is "expensive" I/O)
        // 2. Check cache by ID found in file.
        // 3. Return cached version if exists (saving parsing/object overhead if we were
        // just peeking),
        // BUT actually, to save I/O we should probably cache by "Process ID" and the
        // request should come in as "Run Process X".
        // Since the current FlowEngine request starts with a file path, we'll do this:

        // Simulating: Request comes in for "Process X" (we'll just use a hardcoded scan
        // or map for now)
        // For simplicity: We WILL load the JSON first to get the ID, then check cache.
        // Ideally, you'd request getProcess("process-123"), and the service looks up
        // "process-123" in cache.
        // If missing, it goes to DB/File.

        // Let's modify to assume the 'filePath' is the source of truth for the
        // definition.

        // Optimized approach for this demo file-based structure:
        // We will cache by FilePath for now to avoid re-reading the file.
        // Or better: Load the file, get ID, check cache.

        CallProcessNode loaded = JsonLoader.loadProcess(filePath);
        String processId = loaded.getId();

        Optional<CallProcessNode> cached = processCache.getProcess(processId);
        if (cached.isPresent()) {
            System.out.println("[Service] Cache HIT for " + processId);
            return cached.get();
        } else {
            System.out.println("[Service] Cache MISS for " + processId + ". Caching now.");
            processCache.cacheProcess(loaded);
            return loaded;
        }
    }
}

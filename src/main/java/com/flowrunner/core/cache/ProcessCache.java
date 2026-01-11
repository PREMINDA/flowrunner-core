package com.flowrunner.core.cache;

import com.flowrunner.core.model.ProcessNode;
import java.util.Optional;

public interface ProcessCache {
    Optional<ProcessNode> getProcess(String id);

    void cacheProcess(ProcessNode process);
}

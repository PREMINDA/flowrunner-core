package com.flowrunner.core.cache;

import com.flowrunner.core.model.CallProcessNode;
import java.util.Optional;

public interface ProcessCache {
    Optional<CallProcessNode> getProcess(String id);

    void cacheProcess(CallProcessNode process);
}

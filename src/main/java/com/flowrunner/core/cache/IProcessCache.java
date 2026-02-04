package com.flowrunner.core.cache;

import com.flowrunner.core.model.FlowDef;
import java.util.Optional;

public interface IProcessCache {
    Optional<FlowDef> getProcess(String id);

    void cacheProcess(FlowDef process);
}

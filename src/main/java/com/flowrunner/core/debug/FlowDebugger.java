package com.flowrunner.core.debug;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject; // Or use Jackson/Gson

public class FlowDebugger {
    private static Set<String> breakpoints = new HashSet<>();
    private static long lastModified = 0;
    private static final File BREAKPOINT_FILE = new File(".flow-breakpoints.json");

    public static void checkBreakpoint(String nodeId, String vars) {
        // 1. Refresh Breakpoints if file changed
        reloadBreakpoints();

        // 2. CHECK: Only pause if in the list
        if (breakpoints.contains(nodeId)) {
            System.out.println("@@FLOW:PAUSED:" + nodeId + ":" + vars);
            pauseForDebug();
        }else{
            System.out.println("--------------------------------------");
        }
    }

    private static void reloadBreakpoints() {
        if (BREAKPOINT_FILE.exists() && BREAKPOINT_FILE.lastModified() > lastModified) {
            try {
                String content = new String(Files.readAllBytes(BREAKPOINT_FILE.toPath()));
                JSONObject json = new JSONObject(content); // Adapt to your JSON lib
                
                breakpoints.clear();
                for (Object id : json.getJSONArray("breakpoints")) {
                    breakpoints.add(id.toString());
                }
                lastModified = BREAKPOINT_FILE.lastModified();
                System.out.println("[FlowDebugger] Reloaded breakpoints: " + breakpoints);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void pauseForDebug() {
        // Breakpoint trapthre
             System.out.println(">>> PAUSED FOR DEBUG (5s) <<<");
        try {
            Thread.sleep(20000); // Wait 5 seconds so you can see the UI update
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
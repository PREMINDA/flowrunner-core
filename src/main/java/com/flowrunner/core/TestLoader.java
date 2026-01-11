package com.flowrunner.core;

import com.flowrunner.config.AppConfig;
import com.flowrunner.core.engine.FlowEngine;
import com.flowrunner.core.model.CallProcessNode;
import com.flowrunner.core.service.ProcessService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class TestLoader {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        FlowEngine engine = context.getBean(FlowEngine.class);
        ProcessService processService = context.getBean(ProcessService.class);

        try {
            String jsonPath = "processes/asd.json";

            System.out.println("--- Run 1 (Fetch from Resource) ---");
            CallProcessNode process1 = processService.getProcess(jsonPath);
            engine.run(process1);

            System.out.println("\n--- Run 2 (Fetch from Cache) ---");
            // The service checks cache by ID. Since process1 was loaded and cached,
            // the second call should hit the cache if the ID matches.
            // Note: Our service logic currently loads file to get ID then checks cache.
            // This demonstrates the repo pattern logic.
            CallProcessNode process2 = processService.getProcess(jsonPath);
            engine.run(process2);

        } catch (IOException e) {
            System.err.println("Failed to load JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

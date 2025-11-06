package com.example.springboot.componenttest;

import com.intuit.karate.Results;
import com.intuit.karate.core.MockServer;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExampleApplicationTestSuite {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ExampleApplicationTestSuite.class);
    private static final String CLASSPATH="classpath:";


    @BeforeAll
    public static void setup() {
        startMockServer();
        startServer();
    }

    @Test
    void runRegression() {
        Results results= Karate.run().relativeTo(getClass()).outputCucumberJson(true).tags("~@ignore").parallel(1);
        generateReport(results.getReportDir());
        assertEquals(0, results.getFailCount(), results.getErrorMessages());

    }

    private void generateReport(String reportDir) {
        final Collection<File> jsonFiles = org.apache.commons.io.FileUtils.listFiles(new File(reportDir), new String[]{"json"}, true);
        final List<String> jsonPaths= new java.util.ArrayList<>(jsonFiles.size());
        jsonFiles.forEach(file-> jsonPaths.add(file.getAbsolutePath()));
        final String serviceName= "example-service";
        final net.masterthought.cucumber.Configuration config= new net.masterthought.cucumber.Configuration(new File("build"), serviceName);
        final net.masterthought.cucumber.ReportBuilder reportBuilder= new net.masterthought.cucumber.ReportBuilder(jsonPaths, config);

    }

    private static void startServer() {
        start(new String[]{"--server.port=8080", "--spring.profiles.active=componenttest"},"com.example.springboot.Application","main");
    }

    private static void startMockServer() {
        String path = ExampleApplicationTestSuite.class.getPackageName().replace('.', '/');
        MockServer.feature(CLASSPATH + path + "/mockserver.feature").http(8081).build();
        logger.info("Mock server started on port 8081");
    }

    public static void start(String[] args, String className,String methodName) {
        try {
            Class<?>[] argTypes = new Class[]{String[].class};
            Class<?> clazz = Class.forName(className);
            Method m = clazz.getDeclaredMethod(methodName, argTypes);
            String[] mainArgs = Arrays.copyOfRange(args,1, args.length);
            Object r= m.invoke(null, (Object) mainArgs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
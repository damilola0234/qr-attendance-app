package com.example.qrattendance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Simple configuration logger that shows database connection details on startup.
 * Provides H2 console access information when running with H2 in-memory database.
 */
@Component
public class DatasourceChecker implements CommandLineRunner {

    private final Environment env;

    public DatasourceChecker(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        String url = env.getProperty("spring.datasource.url", "");
        String username = env.getProperty("spring.datasource.username", "");
        
        System.out.println("\n=== Database Configuration ===");
        System.out.println("JDBC URL: " + url);
        System.out.println("Username: " + username);
        
        if (url.contains("h2:mem")) {
            System.out.println("\n=== H2 Database Information ===");
            System.out.println("Using H2 in-memory database");
            System.out.println("H2 Console: http://localhost:8080/h2-console");
            System.out.println("JDBC URL: jdbc:h2:mem:qrattendance");
            System.out.println("Username: sa");
            System.out.println("Password: password");
            System.out.println("================================\n");
        }
    }
}
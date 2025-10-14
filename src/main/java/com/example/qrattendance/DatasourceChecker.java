package com.example.qrattendance;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

@Component
public class DatasourceChecker implements CommandLineRunner {

    private final Environment env;

    public DatasourceChecker(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        String url = env.getProperty("spring.datasource.url");
        String host = env.getProperty("DB_HOST");
        String portStr = env.getProperty("DB_PORT", "5432");
        String user = env.getProperty("DB_USER");
        String pass = env.getProperty("DB_PASSWORD");

        System.out.println("Resolved spring.datasource.url=" + url);
        System.out.println("Resolved DB_HOST=" + host);
        System.out.println("Resolved DB_PORT=" + portStr);
        System.out.println("Resolved DB_NAME=" + env.getProperty("DB_NAME"));
        System.out.println("Resolved DB_USER=" + user);
        System.out.println("Resolved DB_PASSWORD=" + (pass == null ? "null" : "****"));

        if (host == null || host.isBlank()) {
            System.out.println("DB_HOST is not set");
            return;
        }

        // DNS resolution check
        try {
            InetAddress addr = InetAddress.getByName(host);
            System.out.println("DNS: " + host + " -> " + addr.getHostAddress());
        } catch (Exception e) {
            System.out.println("DNS resolution failed for host: " + host + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            return;
        }

        // TCP connectivity check
        int port = Integer.parseInt(portStr);
        try (Socket s = new Socket()) {
            s.connect(new java.net.InetSocketAddress(host, port), 2000);
            System.out.println("TCP: Connected to " + host + ":" + port);
        } catch (Exception e) {
            System.out.println("TCP connection failed to " + host + ":" + port + " -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            return;
        }

        // Quick JDBC connect (short timeout)
        if (url != null && user != null) {
            try {
                DriverManager.setLoginTimeout(5); // seconds
                try (Connection c = DriverManager.getConnection(url, Objects.toString(user, ""), Objects.toString(pass, ""))) {
                    System.out.println("JDBC: Connection successful (autocommit=" + c.getAutoCommit() + ")");
                }
            } catch (Exception e) {
                System.out.println("JDBC connection failed -> " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        } else {
            System.out.println("Skipping JDBC attempt: missing url or user");
        }
    }
}
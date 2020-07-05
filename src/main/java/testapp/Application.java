package testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class: Application
 *
 * Identifies the application as a Spring Boot application, which can be run stand-alone.
 *
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
	SpringApplication.run(Application.class, args);
    }
}

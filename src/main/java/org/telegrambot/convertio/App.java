package org.telegrambot.convertio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegrambot.convertio.core.frameworks.SpringConfig;

// An annotation that combines @Configuration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class App {
    private static final int listener_port = 8085;

    // This is a method that directly launches the entire application and connects to the bot in a telegram
    public static void main(String[] args) {
        SpringApplication.run(SpringConfig.class, args);
    }
}

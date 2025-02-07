package ru.y.pivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
@ComponentScan("ru.y.pivo.*")
public class Application {

    public static void main(String[] args) {
        System.setProperty("java.net.preferIPv4Stack", "true");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ViewResolver getViewResolver(ResourceLoader resourceLoader) {
        MustacheViewResolver mustacheViewResolver
                = new MustacheViewResolver();
        mustacheViewResolver.setPrefix("templates/");
        mustacheViewResolver.setSuffix(".mustache");
        mustacheViewResolver.setCache(false);

        return mustacheViewResolver;
    }

}

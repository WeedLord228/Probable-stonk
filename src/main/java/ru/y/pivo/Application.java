package ru.y.pivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
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

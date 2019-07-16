package com.shiju.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.converter.Converter;

import java.util.Map;

@SpringBootApplication
public class SpringRecipeApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringRecipeApplication.class, args);
        Map<String, Converter> beans = context.getBeansOfType(Converter.class);
        System.out.println("Found Beans" + beans.size());
    }

}

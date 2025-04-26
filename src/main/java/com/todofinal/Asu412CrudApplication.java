package com.todofinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class })
public class Asu412CrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(Asu412CrudApplication.class, args);
    }

}

package com.koi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@MapperScan("com.koi.mapper")
@ComponentScan(basePackages = {"com.koi"})
//外部tomcat
/*public class KoiblogApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(KoiblogApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KoiblogApplication.class);
    }
}*/
//内部tomcat
public class KoiblogApplication  {
    public static void main(String[] args) {
        SpringApplication.run(KoiblogApplication.class, args);
    }
}

package sda_tema_2_spring.Tema_2.config;

import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableSpringDataWebSupport
@EnableWebMvc
@Component
public class AppWebConfig implements WebMvcConfigurer {
}

package cat.itb.projecte1.apiusuaris.configuracio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//Documentació aquí
//https://spring.io/guides/gs/rest-service-cors/
//Exemple d'habilitar Cross Origins a nivell de controlador, no de mètode.

@Configuration
public class ConfiguracioWebApi {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/usuaris/**")
                        .allowedOrigins("http://localhost:9001")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .maxAge(3600);
            }

        };
    }
}

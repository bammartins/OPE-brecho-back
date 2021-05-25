package br.com.gerenciamento.estoque.config;

import br.com.gerenciamento.estoque.services.DBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public boolean instantiateDatabase(DBService dbService) throws ParseException {
        dbService.instantiateTestDatabase();
        return true;
    }

}

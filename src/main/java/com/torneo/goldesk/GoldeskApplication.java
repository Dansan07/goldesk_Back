package com.torneo.goldesk;

import com.torneo.goldesk.Repository.EquipoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class GoldeskApplication {

	public static void main(String[] args) {
		// Carga el archivo .env y lo inyecta en las variables de sistema
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		
		SpringApplication.run(GoldeskApplication.class, args);

	}
	@Bean
	CommandLineRunner probarEquipos(EquipoRepository repo) {
		return args -> {
			System.out.println("=== EQUIPOS ACTIVOS ===");
			repo.findAll().forEach(e -> {
				System.out.println(
						e.getIdEquipo() + " | " +
								e.getNombreEquipo() + " | " +
								e.getCodigoEquipo() + " | " +
								e.getActivo()
				);
			});
		};
	}


}

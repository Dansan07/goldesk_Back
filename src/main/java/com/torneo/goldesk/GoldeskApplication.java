package com.torneo.goldesk;

import com.torneo.goldesk.Repository.EquipoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GoldeskApplication {

	public static void main(String[] args) {
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

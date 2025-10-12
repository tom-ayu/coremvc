package com.udla.proyectofin_web_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import com.udla.proyectofin_web_mvc.repositorio.UsuarioRepository;
import com.udla.proyectofin_web_mvc.modelo.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProyectofinWebMvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProyectofinWebMvcApplication.class, args);
	}

    // Crea usuario admin si no existe, facilita pruebas sin SQL
    @Bean
    public CommandLineRunner dataLoader(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("Admin123!"));
                admin.setRol("ADMIN");
                admin.setEnabled(true);
                repo.save(admin);
            }
        };
    }

}
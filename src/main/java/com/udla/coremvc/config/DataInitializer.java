package com.udla.coremvc.config;

import com.udla.coremvc.modelo.Usuario;
import com.udla.coremvc.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("prod")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear usuario ADMIN si no existe
            if (usuarioRepository.findByUsername("admin") == null) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin123!"));
                admin.setRol("ADMIN");
                admin.setEnabled(true);
                usuarioRepository.save(admin);
                System.out.println("------- Usuario ADMIN creado -------");
            }

            // Crear usuario USER de prueba
            if (usuarioRepository.findByUsername("usuario") == null) {
                Usuario user = new Usuario();
                user.setUsername("usuario");
                user.setPassword(passwordEncoder.encode("User123!"));
                user.setRol("USER");
                user.setEnabled(true);
                usuarioRepository.save(user);
                System.out.println("------- Usuario USER creado -------");
            }
        };
    }
}
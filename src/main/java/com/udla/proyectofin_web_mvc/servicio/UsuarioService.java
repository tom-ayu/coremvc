package com.udla.proyectofin_web_mvc.servicio;

import com.udla.proyectofin_web_mvc.modelo.Usuario;
import com.udla.proyectofin_web_mvc.repositorio.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Login
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // CRUD
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void guardar(Usuario usuario) {
        // Evita doble encode (si la contraseña no parece estar en BCrypt -> se codifica)
        String pwd = usuario.getPassword();
        if (pwd != null && !pwd.startsWith("$2a$") && !pwd.startsWith("$2b$") && !pwd.startsWith("$2y$")) {
            usuario.setPassword(passwordEncoder.encode(pwd));
        }
        usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}

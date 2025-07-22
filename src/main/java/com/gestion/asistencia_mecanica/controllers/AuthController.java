package com.gestion.asistencia_mecanica.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.asistencia_mecanica.dto.login.LoginRequest;
import com.gestion.asistencia_mecanica.models.User;
import com.gestion.asistencia_mecanica.security.tokenjwt.JwtLoginResponse;
import com.gestion.asistencia_mecanica.security.tokenjwt.JwtUtil;
import com.gestion.asistencia_mecanica.service.CustomUserDetailsService;
import com.gestion.asistencia_mecanica.service.interfaces.CustomUserDetails;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpResponse) {
        try {
            log.info("Intento de login para email: {}", loginRequest.getEmail());

            // Autenticación
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()));

            // Obtenemos los datos del usuario autenticado
            CustomUserDetails userDetails = (CustomUserDetails) userDetailsService
                    .loadUserByUsername(loginRequest.getEmail());

            // Generamos el token JWT
            String token = jwtUtil.generateToken(userDetails);

            // Create HTTP-only cookie
            Cookie jwtCookie = new Cookie("jwtToken", token); // Choose a name for your cookie
            jwtCookie.setHttpOnly(true); // Crucial for security
            jwtCookie.setSecure(true); // Use true in production with HTTPS
            jwtCookie.setPath("/"); // Make it accessible from all paths
            jwtCookie.setMaxAge((int) (jwtUtil.getJwtExpirationMs() / 1000)); // Set expiration in seconds (cast to int)

            httpResponse.addCookie(jwtCookie); // Add the cookie to the response

            // Devolvemos el token + info adicional
            User user = userDetails.getUser();
            JwtLoginResponse response = new JwtLoginResponse(token, user.getRol().getNombre().name(), user.getUserId());

            log.info("Login exitoso para usuario: {}", loginRequest.getEmail());
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            log.warn("Credenciales incorrectas para email: {}", loginRequest.getEmail());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (AuthenticationException e) {
            log.error("Error de autenticación: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error de autenticación");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);

        } catch (Exception e) {
            log.error("Error inesperado durante el login: ", e);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error interno del servidor");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
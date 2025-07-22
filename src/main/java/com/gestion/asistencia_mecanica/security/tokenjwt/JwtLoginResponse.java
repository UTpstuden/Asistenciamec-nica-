package com.gestion.asistencia_mecanica.security.tokenjwt;

public record JwtLoginResponse(String token, String rol, Integer userId) {
}
// This record represents the response sent to the client after a successful
// login.
// It contains the JWT token, the role of the user, and the user's ID.
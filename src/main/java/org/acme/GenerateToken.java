package org.acme;

import io.smallrye.jwt.build.Jwt;
import java.time.Duration;

public class GenerateToken {

    public static String generateToken(String username,  String role){
        if (username == null || role == null)
            throw new SecurityException("Credenciais inválidas");


        String token = Jwt.issuer("auth-login")
                .upn(username)
                .groups(role)
                .expiresIn(Duration.ofHours(24))
                .sign();

        return token; //retorna o token gerado
        }
    }


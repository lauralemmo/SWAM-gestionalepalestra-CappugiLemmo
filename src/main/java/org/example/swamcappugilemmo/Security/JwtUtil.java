package org.example.swamcappugilemmo.Security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // Usa una chiave segreta sicura e possibilmente salvata nelle variabili d'ambiente
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1 giorno in millisecondi

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }
    //controlla se il token è valido
    public static void validateToken(String token) {
        // Se il token è falso, manomesso o scaduto, questa riga lancia un'eccezione
        Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
    }
}
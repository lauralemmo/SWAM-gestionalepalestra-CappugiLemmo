package org.example.swamcappugilemmo.Security;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Admin;
import org.mindrot.jbcrypt.BCrypt;
import org.example.swamcappugilemmo.DomainModel.User;
import org.example.swamcappugilemmo.DomainModel.PersonalTrainer;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.Security.JwtUtil;
import java.util.HashMap;
import java.util.Map;

@Dependent
public class AuthenticationController {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Map<String, Object> login(String username, String password) {
        try {
            // Cerca l'utente in tutte le tabelle grazie all'ereditarietà di User
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :un", User.class)
                    .setParameter("un", username)
                    .getSingleResult();

            // Controlla la password con BCrypt
            if (BCrypt.checkpw(password, user.getPassword())) {

                // Determina il ruolo in base a quale tabella appartiene l'oggetto
                String role;
                if (user instanceof PersonalTrainer) {
                    role = "PT";
                } else if (user instanceof Athlete) {
                    role = "ATHLETE";
                } else if (user instanceof Admin) {
                    role = "ADMIN";
                } else {
                    throw new IllegalArgumentException("Ruolo utente sconosciuto.");
                }
                // Genera il token mettendo dentro il ruolo
                String token = JwtUtil.generateToken(user.getUsername(), role);

                // Prepara la risposta
                Map<String, Object> response = new HashMap<>();
                response.put("token", token);
                response.put("role", role);
                response.put("username", user.getUsername());

                return response;
            } else {
                throw new IllegalArgumentException("Password errata.");
            }
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Utente non trovato.");
        }
    }
}

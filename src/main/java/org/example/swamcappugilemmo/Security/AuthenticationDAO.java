package org.example.swamcappugilemmo.Security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.User;

@ApplicationScoped
public class AuthenticationDAO {

    @PersistenceContext
    private EntityManager em;

        public User findByUsername(String username) {
            try {
                return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (jakarta.persistence.NoResultException e) {
                return null; // Restituisce null se non viene trovato un utente con il nome utente specificato
            }
        }
}

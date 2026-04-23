package org.example.swamcappugilemmo.Util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Admin;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class DatabaseInitializer {

    // Iniettiamo direttamente l'EntityManager per parlare col database
    @PersistenceContext
    private EntityManager em;

    // Il metodo @Transactional è fondamentale qui perché stiamo scrivendo nel DB
    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        System.out.println("⏳ Controllo se esiste già un Admin nel database...");

        // Conta quanti admin ci sono
        Long adminCount = em.createQuery("SELECT COUNT(a) FROM Admin a", Long.class).getSingleResult();

        if (adminCount == 0) {
            System.out.println("⚙️ Nessun admin trovato. Creazione dell'admin di default...");

            Admin defaultAdmin = new Admin();
            defaultAdmin.setName("Super");
            defaultAdmin.setSurname("Admin");
            defaultAdmin.setUsername("admin");

            // Attenzione: criptiamo la password prima di salvarla!
            String hashedPassword = BCrypt.hashpw("admin123", BCrypt.gensalt());
            defaultAdmin.setPassword(hashedPassword);

            defaultAdmin.setEmail("admin@palestra.com");
            defaultAdmin.setTax_code("ADMIN00000000000");

            // Salviamo nel DB
            em.persist(defaultAdmin);

            System.out.println("✅ Amministratore creato con successo!");
            System.out.println("👤 Username: admin");
            System.out.println("🔑 Password: admin123");
        } else {
            System.out.println("✅ Admin già presente nel database. Nessuna azione necessaria.");
        }
    }
}
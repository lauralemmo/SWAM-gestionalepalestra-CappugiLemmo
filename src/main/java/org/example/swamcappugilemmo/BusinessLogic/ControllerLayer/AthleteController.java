package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.AthleteMapper;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Subscription;
import org.example.swamcappugilemmo.DomainModel.SubscriptionType;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Dependent
public class AthleteController {
    @Inject
    private AthleteDAO athleteDAO;

    @Inject
    private AthleteMapper athleteMapper;

    /// ////////////// ATHLETE MANAGEMENT ///////////////
    /*@Transactional
    public void registerNewAthlete(String name, String surname, String username, String password, String email, String phone_number,
                                String tax_code, LocalDate birth_date, String height, String weight, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        Athlete newAthlete = new Athlete(tax_code,
                name,
                surname,
                username,
                password,
                email,
                phone_number,
                birth_date,
                height,
                weight);
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    } //vecchio metodo, ora uso quello con i mapper

    @Transactional
    public void registerNewAthlete(Athlete newAthlete, SubscriptionType subscriptionType, LocalDate startDate) {
        Subscription initialSubscription = new Subscription(subscriptionType, startDate);
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
    }*/

    @Transactional
    public AthleteResponseDTO registerNewAthlete(AthleteRequestDTO request) {
        Athlete newAthlete = athleteMapper.toEntity(request);

        //cripta la password prima di salvarla
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        newAthlete.setPassword(hashedPassword);

        Subscription initialSubscription = new Subscription();
        SubscriptionType type = request.getSubscriptionType();
        LocalDate start = request.getStartDate();

        //gestione della subscription
        if (type != null && start != null) {
            initialSubscription.setType(type);
            initialSubscription.setStart_date(start);
            initialSubscription.setPrice(type.getDefaultPrice());
            initialSubscription.setEnd_date(start.plusMonths(type.getMonths()));
        }
        newAthlete.addSubscription(initialSubscription);
        athleteDAO.saveAthlete(newAthlete);
        return athleteMapper.toDto(newAthlete);
    }

    @Transactional
    public AthleteResponseDTO loginAthlete(String username, String password) {
        Athlete athlete = athleteDAO.findAthleteByUsername(username);
        if (athlete != null && BCrypt.checkpw(password, athlete.getPassword())) {
            return athleteMapper.toDto(athlete);
        } else {
            throw new IllegalArgumentException("Credenziali non valide: username o password errati.");
        }
    }
    @Transactional
    public List<AthleteResponseDTO> getAllAthletes() {
        return athleteDAO.findAll()
                .stream()
                .map(athleteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public AthleteResponseDTO getAthleteById(Long id) {
        Athlete athlete = athleteDAO.findById(id);
        if (athlete != null) {
            return athleteMapper.toDto(athlete);
        } else {
            return null;
        }
    }

    @Transactional
    public AthleteResponseDTO getAthleteByTaxCode(String tax_code) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            return athleteMapper.toDto(athlete);
        } else {
            return null; // O gestisci l'errore come preferisci
        }
    }

    @Transactional
    public void updateAthleteUsername(String username, String request) {
        Athlete athlete = athleteDAO.findAthleteByUsername(username);
        if (athlete != null && !request.isBlank()) {
            athlete.setUsername(request);
        } else {
            throw new IllegalArgumentException("Atleta con username: " + username + " non trovato o username non valido.");
        }
    }


    @Transactional
    public void deleteAthleta(Long Id){
        athleteDAO.deleteAthlete(Id);
    }

    @Transactional
    public void updateAthleteUsernameSecurely(String username, String newUsername, String callerUsername, boolean isAdmin) {
        if (!isAdmin && !username.equals(callerUsername)) {
            throw new SecurityException("Accesso negato: Non puoi modificare il profilo di un altro atleta!");
        }

        Athlete athlete = athleteDAO.findAthleteByUsername(username);

        // Se l'atleta esiste e il nuovo username è valido, aggiorniamo; altrimenti lanciamo un'eccezione
        if (athlete != null && newUsername != null && !newUsername.isBlank()) {
            athlete.setUsername(newUsername);

        } else {
            throw new IllegalArgumentException("Atleta non trovato o nuovo username non valido.");
        }
    }
   /* @Transactional
    public void updateAthleteInfo(String tax_code, String height, String weight) {
        Athlete athlete = athleteDAO.findAthleteByTaxCode(tax_code);
        if (athlete != null) {
            athlete.setHeight(height);
            athlete.setWeight(weight);
            athleteDAO.updateAthlete(athlete);
        }
    }*/

   /* @Transactional
    public void updateAthlete(String tax_code, String name, String surname, String username, String password, String email,
                              String phone_number, LocalDate birth_date, String height, String weight,
                              SubscriptionType subscriptionType, LocalDate startDate){
        Athlete athlete = new Athlete(tax_code, name, surname, username, password, email, phone_number, birth_date, height, weight);
        athleteDAO.updateAthlete(athlete);
    }*/

}

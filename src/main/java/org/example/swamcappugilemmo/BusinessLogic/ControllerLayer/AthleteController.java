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
    public void deleteAthleta(Long Id){
        athleteDAO.deleteAthlete(Id);
    }

    @Transactional
    public void updateAthleteUsernameSecurely(String username, String newUsername, String callerUsername, boolean isAdmin) {
        if (!isAdmin && !username.equals(callerUsername)) {
            throw new SecurityException("Accesso negato: Non puoi modificare il profilo di un altro atleta!");
        }

        Athlete athlete = athleteDAO.findAthleteByUsername(username);

        if (athlete != null && newUsername != null && !newUsername.isBlank()) {
            athlete.setUsername(newUsername);

        } else {
            throw new IllegalArgumentException("Atleta non trovato o nuovo username non valido.");
        }
    }

    @Transactional
    public AthleteResponseDTO updateAthleteProfile(Long id, AthleteRequestDTO request, String callerUsername, boolean isAdmin) {
        Athlete athleteToUpdate = athleteDAO.findById(id);
        if (athleteToUpdate == null) {
            throw new IllegalArgumentException("Atleta con ID " + id + " non trovato.");
        }

        if (!isAdmin && !athleteToUpdate.getUsername().equals(callerUsername)) {
            throw new SecurityException("Accesso negato: puoi modificare solo il tuo profilo.");
        }

        athleteToUpdate.setName(request.getName());
        athleteToUpdate.setSurname(request.getSurname());
        athleteToUpdate.setUsername(request.getUsername());
        athleteToUpdate.setEmail(request.getEmail());
        athleteToUpdate.setPhone_number(request.getPhone_number());
        athleteToUpdate.setTax_code(request.getTax_code());
        athleteToUpdate.setBirth_date(request.getBirth_date());
        athleteToUpdate.setHeight(request.getHeight());
        athleteToUpdate.setWeight(request.getWeight());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
            athleteToUpdate.setPassword(hashedPassword);
        }

        athleteDAO.updateAthlete(athleteToUpdate);
        return athleteMapper.toDto(athleteToUpdate);
    }
}
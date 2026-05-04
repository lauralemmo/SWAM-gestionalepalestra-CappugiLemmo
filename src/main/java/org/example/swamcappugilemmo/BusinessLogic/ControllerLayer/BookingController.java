package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseWorkoutPlanResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.BookingMapper; // Import del nuovo mapper
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.BookingDAO;
import org.example.swamcappugilemmo.DomainModel.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Dependent
public class BookingController {

    @Inject
    private BookingDAO bookingDAO;
    @Inject
    private AthleteDAO athleteDAO;
    @Inject
    private CourseDAO courseDAO;
    @Inject
    private BookingMapper bookingMapper;



    @Transactional
    public void createBooking(BookingRequestDTO request, String callerUsername) {
        // Recupero delle entità necessarie tramite i DAO
        Athlete athlete = athleteDAO.findById(request.getAthleteId());
        Course course = courseDAO.getCourseByIdforUpdate(request.getCourseId());

        if (athlete == null || course == null) {
            throw new IllegalArgumentException("Atleta o Corso non trovato");
        }

        // true se l'atleta ha già una prenotazione per quel corso
        boolean alreadyBooked = athlete.getBookings().stream()
                .anyMatch(b -> b.getCourse().getIdCourse().equals(course.getIdCourse()));
        // se true, lancia eccezione
        if (alreadyBooked) {
            throw new IllegalStateException("Sei già iscritto a questo corso!");
        }

        if (course.getNumMembers() < course.getNumMax()) {
            course.setNumMembers(course.getNumMembers() + 1);
        }
        else throw new IllegalStateException("Corso pieno, non è possibile prenotare");

        if (course.getOccurrences().stream().noneMatch(occurrence -> occurrence.getDate()
                .equals(request.getDate()) && occurrence.getHours().equals(request.getHours()))) {
            throw new IllegalArgumentException("Il corso non è disponibile in quella data e ora");
        }

        // Utilizzo del Mapper per creare l'entità Booking
        Booking booking = bookingMapper.toEntity(request, course, athlete);

        /*// Aggiunta della prenotazione all'atleta
        athlete.addBookings(booking);
        // Aggiunta della prenotazione al corso
        course.addBookings(booking);*/

        // Salvataggio tramite DAO
        bookingDAO.saveBooking(booking);
    }


    @Transactional
    public BookingResponseDTO getBookingDTOfromId(Long bookingId) {
        Booking booking = bookingDAO.findBookingById(bookingId);
        return bookingMapper.toDto(booking);
    }

    @Transactional
    public List<BookingResponseDTO> getBookingsByAthleteUsername(String username) {
        // Cerchiamo l'atleta tramite username
        Athlete athlete = athleteDAO.findAthleteByUsername(username);
        if (athlete == null) {
            throw new IllegalArgumentException("Atleta non trovato");
        }

        // Lista di prenotazioni e la mappiamo in DTO
        return athlete.getBookings().stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public BookingResponseDTO updateBooking(BookingRequestDTO request, Long id) {
        Booking b = bookingDAO.findBookingById(id);
        b.setDate(request.getDate());
        b.setHours(request.getHours());
        b.setCourse(courseDAO.getCourseByIdforUpdate(request.getCourseId()));
        b.setAthlete(athleteDAO.findById(request.getAthleteId()));
        Booking updatedB = bookingDAO.updateBooking(b);
        return bookingMapper.toDto((updatedB));
    }

    @Transactional
    public void deleteBooking(Long bookingId, String callerUsername) {
        Booking booking = bookingDAO.findBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
        if (!booking.getAthlete().getUsername().equals(callerUsername)) {
            throw new IllegalStateException("Non hai il permesso di cancellare questa prenotazione");
        }

        // Blocchiamo il corso per assicurarci che nessuno si prenoti mentre liberiamo il posto
        Course course = courseDAO.getCourseByIdforUpdate(booking.getCourse().getIdCourse());
        int current = course.getNumMembers();
        if (current <= 0) {
            throw new IllegalStateException("Numero membri non valido");
        }
        else course.setNumMembers(current - 1);

        // coerenza della memoria
        booking.getAthlete().getBookings().remove(booking);
        course.getBookings().remove(booking);

        // Cancellazione vera e propria della prenotazione
        bookingDAO.deleteBooking(bookingId);
    }


}
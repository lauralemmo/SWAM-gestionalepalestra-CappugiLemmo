package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@ApplicationScoped
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
        Athlete athlete = athleteDAO.findAthleteByUsername(callerUsername);
        request.setAthleteId(athlete.getIdUser());
        Course course = courseDAO.getCourseById(request.getCourseId());

        if (athlete == null || course == null) {
            throw new IllegalArgumentException("Atleta o Corso non trovato");
        }

        // true se l'atleta ha già una prenotazione per quel corso
        boolean alreadyBooked = athlete.getBookings().stream()
                .anyMatch(b -> b.getCourse().getIdCourse().equals(course.getIdCourse())
                        && b.getDate().equals(request.getDate())
                        && b.getHours().equals(request.getHours()));
        // se true, lancia eccezione
        if (alreadyBooked) {
            throw new IllegalStateException("Sei già iscritto a questo corso!");
        }

        if (course.getOccurrences().stream().noneMatch(occurrence ->
                occurrence.getDayOfWeek().equals(request.getDate().getDayOfWeek())
                        && occurrence.getHours().equals(request.getHours()))) {
            throw new IllegalArgumentException("Il corso non è disponibile in quella data e ora");
        }

       /* boolean bookPace = courseDAO.bookPlaceOnACourse(request.getCourseId());
        if (!bookPace) {
            throw new IllegalStateException("Il corso è pieno");
        }*/

        long currentBookings = bookingDAO.countBookingsForLesson(request.getCourseId(), request.getDate(), request.getHours());
        if (currentBookings >= course.getNumMax()) {
            throw new IllegalStateException("La lezione selezionata è già al completo!");
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
        Booking booking = bookingDAO.findBookingById(id);
        if (booking == null) {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }

        Course currentCourse = booking.getCourse();
        Course newCourse = courseDAO.getCourseById(request.getCourseId());

        if (newCourse == null) {
            throw new IllegalArgumentException("Il corso selezionato non esiste");
        }

        boolean lessonChanged = !currentCourse.getIdCourse().equals(newCourse.getIdCourse())
                || !booking.getDate().equals(request.getDate())
                || !booking.getHours().equals(request.getHours());

        if (lessonChanged) {
            // Verifichiamo se la nuova data e ora corrispondono a una lezione (occorrenza) reale del nuovo corso
            boolean orarioValido = newCourse.getOccurrences().stream().anyMatch(o ->
                    o.getDayOfWeek().equals(request.getDate().getDayOfWeek())
                            && o.getHours().equals(request.getHours()));

            if (!orarioValido) {
                throw new IllegalArgumentException("Il corso non è disponibile nella data e ora selezionate");
            }

            // verifichiamo se ci sono posti disponibili nella NUOVA lezione destinazione
            long currentBookings = bookingDAO.countBookingsForLesson(newCourse.getIdCourse(), request.getDate(), request.getHours());
            if (currentBookings >= newCourse.getNumMax()) {
                throw new IllegalStateException("La nuova lezione selezionata è già al completo!");
            }
        }

        booking.setDate(request.getDate());
        booking.setHours(request.getHours());
        booking.setCourse(newCourse);
        booking.setAthlete(athleteDAO.findById(request.getAthleteId()));

        Booking updatedB = bookingDAO.updateBooking(booking);
        return bookingMapper.toDto(updatedB);
    }

    @Transactional
    public void deleteBooking(Long bookingId, String callerUsername) {
        /*Booking booking = bookingDAO.findBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
        if (!booking.getAthlete().getUsername().equals(callerUsername)) {
            throw new IllegalStateException("Non hai il permesso di cancellare questa prenotazione");
        }

        boolean postoLiberato = courseDAO.deleteReservedPlace(booking.getCourse().getIdCourse());

        if (!postoLiberato) {
            throw new IllegalStateException("Impossibile cancellare: il numero di membri è già a zero");
        }

        // coerenza della memoria
        booking.getAthlete().getBookings().remove(booking);

        //questo evita il caricamento lazy. prendi l' emtità collegata e togli la prenotazione dalla lista
        Course course = courseDAO.getCourseById(booking.getCourse().getIdCourse());
        course.getBookings().remove(booking);

        // Cancellazione vera e propria della prenotazione
        bookingDAO.deleteBooking(bookingId);*/
        Booking booking = bookingDAO.findBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
        if (!booking.getAthlete().getUsername().equals(callerUsername)) {
            throw new SecurityException("Non hai il permesso di cancellare questa prenotazione");
        }
        if (booking.getAthlete() != null) {
            booking.getAthlete().getBookings().remove(booking);
        }

        Course course = courseDAO.getCourseById(booking.getCourse().getIdCourse());
        if (course != null) {
            course.getBookings().remove(booking);
        }
        // Questo farà scendere automaticamente il conteggio dei posti della lezione al prossimo 'COUNT'
        bookingDAO.deleteBooking(bookingId);
    }


    public long getBookingCountForLesson(Long courseId, LocalDate date, LocalTime hours) {
        return bookingDAO.countBookingsForLesson(courseId, date, hours);
    }
}
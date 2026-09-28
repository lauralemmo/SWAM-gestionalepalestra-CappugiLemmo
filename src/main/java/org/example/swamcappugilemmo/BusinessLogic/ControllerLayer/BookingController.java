package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.BookingMapper;
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.BookingDAO;
import org.example.swamcappugilemmo.DAO.OccurrenceDAO;
import org.example.swamcappugilemmo.DomainModel.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookingController {

    @Inject
    private BookingDAO bookingDAO;
    @Inject
    private AthleteDAO athleteDAO;
    @Inject
    private OccurrenceDAO occurrenceDAO;
    @Inject
    private BookingMapper bookingMapper;


    @Transactional
    public void createBooking(BookingRequestDTO request, String callerUsername) {
        Athlete athlete = athleteDAO.findAthleteByUsername(callerUsername);
        if (athlete == null) {
            throw new IllegalArgumentException("Atleta non trovato");
        }
        request.setAthleteId(athlete.getIdUser());

        Occurrence occurrence = occurrenceDAO.getOccurrenceById(request.getOccurrenceId());
        if (occurrence == null) {
            throw new IllegalArgumentException("Lezione (occorrenza) non trovata");
        }
        Course course = occurrence.getCourse();

        if (request.getDate() == null) {
            throw new IllegalArgumentException("Data mancante");
        }
        if (request.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Non è possibile prenotarsi a lezioni con una data già passata");
        }
        if (!request.getDate().getDayOfWeek().equals(occurrence.getDayOfWeek())) {
            throw new IllegalArgumentException("La data selezionata non corrisponde al giorno di questa lezione");
        }

        // true se l'atleta ha già una prenotazione per QUESTA occorrenza in QUESTA data
        boolean alreadyBooked = athlete.getBookings().stream()
                .anyMatch(b -> b.getOccurrence().getIdOccurrence().equals(occurrence.getIdOccurrence())
                        && b.getDate().equals(request.getDate()));
        if (alreadyBooked) {
            throw new IllegalStateException("Sei già iscritto a questa lezione!");
        }

        long currentBookings = bookingDAO.countBookingsForLesson(occurrence.getIdOccurrence(), request.getDate());
        if (currentBookings >= course.getNumMax()) {
            throw new IllegalStateException("La lezione selezionata è già al completo!");
        }

        Booking booking = bookingMapper.toEntity(request, occurrence, athlete);
        bookingDAO.saveBooking(booking);
    }


    @Transactional
    public BookingResponseDTO getBookingDTOfromId(Long bookingId) {
        Booking booking = bookingDAO.findBookingById(bookingId);
        return bookingMapper.toDto(booking);
    }

    @Transactional
    public List<BookingResponseDTO> getBookingsByAthleteUsername(String username) {
        Athlete athlete = athleteDAO.findAthleteByUsername(username);
        if (athlete == null) {
            throw new IllegalArgumentException("Atleta non trovato");
        }
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

        Occurrence currentOccurrence = booking.getOccurrence();
        Occurrence newOccurrence = occurrenceDAO.getOccurrenceById(request.getOccurrenceId());
        if (newOccurrence == null) {
            throw new IllegalArgumentException("La lezione selezionata non esiste");
        }
        Course newCourse = newOccurrence.getCourse();

        boolean lessonChanged = !currentOccurrence.getIdOccurrence().equals(newOccurrence.getIdOccurrence())
                || !booking.getDate().equals(request.getDate());

        if (lessonChanged) {
            if (!request.getDate().getDayOfWeek().equals(newOccurrence.getDayOfWeek())) {
                throw new IllegalArgumentException("La data selezionata non corrisponde al giorno della nuova lezione");
            }

            long currentBookings = bookingDAO.countBookingsForLesson(newOccurrence.getIdOccurrence(), request.getDate());
            if (currentBookings >= newCourse.getNumMax()) {
                throw new IllegalStateException("La nuova lezione selezionata è già al completo!");
            }
        }

        booking.setDate(request.getDate());
        booking.setOccurrence(newOccurrence);
        booking.setAthlete(athleteDAO.findById(request.getAthleteId()));

        Booking updatedB = bookingDAO.updateBooking(booking);
        return bookingMapper.toDto(updatedB);
    }

    @Transactional
    public void deleteBooking(Long bookingId, String callerUsername) {
        Booking booking = bookingDAO.findBookingById(bookingId);

        if (booking == null) {
            throw new IllegalArgumentException("Prenotazione non trovata");
        }
        if (!booking.getAthlete().getUsername().equals(callerUsername)) {
            throw new SecurityException("Non hai il permesso di cancellare questa prenotazione");
        }
        booking.getAthlete().getBookings().remove(booking);

        bookingDAO.deleteBooking(bookingId);
    }


    public long getBookingCountForLesson(Long occurrenceId, LocalDate date) {
        return bookingDAO.countBookingsForLesson(occurrenceId, date);
    }
}
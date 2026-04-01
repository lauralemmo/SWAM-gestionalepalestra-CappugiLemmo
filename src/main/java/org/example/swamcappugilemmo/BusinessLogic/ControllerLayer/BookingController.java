package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.BookingMapper; // Import del nuovo mapper
import org.example.swamcappugilemmo.DAO.AthleteDAO;
import org.example.swamcappugilemmo.DAO.CourseDAO;
import org.example.swamcappugilemmo.DAO.BookingDAO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Booking;

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
    public void createBooking(BookingDTO request) {
        // Recupero delle entità necessarie tramite i DAO
        Athlete athlete = athleteDAO.findAthleteByTaxCode(request.getTaxCode());

        Course course = courseDAO.getCourseById(request.getIdCourse());

        if (athlete == null || course == null) {
            throw new IllegalArgumentException("Atleta o Corso non trovato");
        }

        if (course.getNumMembers() < course.getNumMax()) {
            course.setNumMembers(course.getNumMembers() + 1);
        }
        else throw new IllegalStateException("Corso pieno, non è possibile prenotare");

        if (course.getOccurrences().stream().noneMatch(occurrence -> occurrence.getDate().equals(request.getDate()) && occurrence.getHours().equals(request.getHours()))) {
            throw new IllegalArgumentException("Il corso non è disponibile in quella data e ora");
        }

        // Utilizzo del Mapper per creare l'entità Booking
        Booking booking = bookingMapper.toEntity(request.getDate(), request.getHours(), course, athlete);

        // Aggiunta della prenotazione all'atleta
        athlete.addBookings(booking);

        // Salvataggio tramite DAO
        bookingDAO.saveBooking(booking);
    }

    @Transactional
    public BookingDTO getBookingDTOfromId(Long bookingId) {
        Booking booking = bookingDAO.findBookingById(bookingId);
        return bookingMapper.toDto(booking);
    }
}
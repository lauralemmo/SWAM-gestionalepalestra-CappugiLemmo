package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Booking;
import org.example.swamcappugilemmo.DomainModel.Exercise;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class BookingDAO {
    @PersistenceContext
    private EntityManager em;

    public void saveBooking(Booking booking) {
        em.persist(booking);
        System.out.println("Prenotazione salvata");
    }

    public Booking findBookingById(Long bookingId) {
        Booking booking = em.find(Booking.class,bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found.");
        }
        return booking;
    }

    public List<Booking> getAllBookings() {
        return em.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    }

    public Booking updateBooking(Booking b) {
        Booking newB = em.merge(b);
        if (newB == null) {
            throw new RuntimeException("Aggiornamento fallito");
        }
        System.out.println("Prenotazione modificata correttamente");
        return newB;
    }

    public void deleteBooking(Long id) {
        Booking booking = findBookingById(id);
        em.remove(booking);
        System.out.println("Prenotazione eliminata");
    }

    public long countBookingsForLesson(Long courseId, LocalDate date, LocalTime hours) {
        return em.createQuery(
                        "SELECT COUNT(b) FROM Booking b WHERE b.course.idCourse = :courseId AND b.date = :date AND b.hours = :hours", Long.class)
                .setParameter("courseId", courseId)
                .setParameter("date", date)
                .setParameter("hours", hours)
                .getSingleResult();
    }
}
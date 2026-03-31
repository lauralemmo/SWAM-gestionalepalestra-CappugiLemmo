package org.example.swamcappugilemmo.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.swamcappugilemmo.DomainModel.Booking;

@ApplicationScoped
public class BookingDAO {
    @PersistenceContext
    private EntityManager em;

    public void saveBooking(Booking booking) {
        em.persist(booking);
    }

    public Booking findBookingById(Long bookingId) {
        Booking booking = em.find(Booking.class,bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found.");
        }
        return booking;
    }
}
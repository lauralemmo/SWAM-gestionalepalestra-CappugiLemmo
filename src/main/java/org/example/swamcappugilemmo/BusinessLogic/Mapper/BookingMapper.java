package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Booking;
import org.example.swamcappugilemmo.DomainModel.Course;

import java.time.LocalDate;
import java.time.LocalTime;

@ApplicationScoped
public class BookingMapper {

    public Booking toEntity(BookingRequestDTO request, Course course, Athlete athlete) {
        Booking b = new Booking();
        b.setDate(request.getDate());
        b.setHours(request.getHours());
        b.setCourse(course);
        b.setAthlete(athlete);
        return b;
    }

    public BookingResponseDTO toDto(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getIdBooking());
        dto.setDate(booking.getDate());
        dto.setHours(booking.getHours());
        dto.setCourseId(booking.getCourse().getIdCourse());
        dto.setAthleteId(booking.getAthlete().getIdUser());

        return dto;
    }
}

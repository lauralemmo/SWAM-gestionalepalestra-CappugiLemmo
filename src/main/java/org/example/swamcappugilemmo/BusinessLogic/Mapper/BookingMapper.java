package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Booking;
import org.example.swamcappugilemmo.DomainModel.Course;
import org.example.swamcappugilemmo.DomainModel.Occurrence;

import java.time.LocalDate;
import java.time.LocalTime;

@ApplicationScoped
public class BookingMapper {

    public Booking toEntity(BookingRequestDTO request, Occurrence occurrence, Athlete athlete) {
        Booking b = new Booking();
        b.setDate(request.getDate());
//        b.setHours(request.getHours());
        b.setOccurrence(occurrence);
        //b.setCourse(course);
        b.setAthlete(athlete);
        return b;
    }

    public BookingResponseDTO toDto(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getIdBooking());
        dto.setDate(booking.getDate());
        dto.setHours(booking.getOccurrence().getHours());
        dto.setOccurrenceId(booking.getOccurrence().getIdOccurrence());
        dto.setCourseId(booking.getOccurrence().getCourse().getIdCourse());
        dto.setCourseName(booking.getOccurrence().getCourse().getName());
        dto.setAthleteId(booking.getAthlete().getIdUser());

        return dto;
    }
}

package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Booking;
import org.example.swamcappugilemmo.DomainModel.Course;

import java.time.LocalDate;
import java.time.LocalTime;

@ApplicationScoped
public class BookingMapper {
    @Inject
    AthleteMapper athleteMapper;

    public Booking toEntity(LocalDate localDate, LocalTime h, Course course, Athlete athlete) {
    return new Booking(localDate ,h , course, athlete) ;
    }
    public BookingDTO toDto(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setTaxCode(athleteMapper.toDto(booking.getAthlete()).getTax_code());
        dto.setIdCourse(booking.getCourse().getId());
        dto.setDate(booking.getDate());
        dto.setHours(booking.getHours());

        return dto;
    }
}

package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.example.swamcappugilemmo.BusinessLogic.DTO.AthleteResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.BookingDTO;
import org.example.swamcappugilemmo.DomainModel.Athlete;
import org.example.swamcappugilemmo.DomainModel.Booking;
import org.example.swamcappugilemmo.DomainModel.Course;

import java.time.LocalDate;

@ApplicationScoped
public class BookingMapper {
    @Inject
    AthleteMapper athleteMapper;

    public Booking toEntity(LocalDate localDate, Course course, Athlete athlete) {
    return new Booking(localDate, course, athlete) ;
    }
}

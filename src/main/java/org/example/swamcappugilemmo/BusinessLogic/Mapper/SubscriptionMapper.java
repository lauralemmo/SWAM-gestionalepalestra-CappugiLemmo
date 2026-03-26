package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionDTO;
import org.example.swamcappugilemmo.DomainModel.Subscription;

import java.time.LocalDate;

@ApplicationScoped
public class SubscriptionMapper {

    public SubscriptionDTO toDto(Subscription s) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setType(s.getType().name());
        dto.setPrice(s.getPrice());
        dto.setActive(s.isActive());
        dto.setStartDate(s.getStart_date());
        dto.setEndDate(s.getEnd_date());
        return dto;
    }
}
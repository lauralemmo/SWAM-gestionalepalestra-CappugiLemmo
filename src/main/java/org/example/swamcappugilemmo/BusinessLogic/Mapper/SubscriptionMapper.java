package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Subscription;

import java.time.LocalDate;

@ApplicationScoped
public class SubscriptionMapper {

    public SubscriptionResponseDTO toDto(Subscription s) {
        SubscriptionResponseDTO dto = new SubscriptionResponseDTO();
        dto.setType(s.getType());
        dto.setPrice(s.getPrice());
        dto.setActive(s.isActive());
        dto.setStartDate(s.getStart_date());
        dto.setEndDate(s.getEnd_date());
        return dto;
    }

    public Subscription toEntity(SubscriptionRequestDTO dto) {
        Subscription s = new Subscription();
        s.setType(dto.getType());
        s.setStart_date(dto.getStartDate());
        s.setEnd_date(dto.getStartDate().plusMonths(dto.getType().getMonths()));
        s.setPrice(dto.getType().getDefaultPrice());
        return s;
    }
}
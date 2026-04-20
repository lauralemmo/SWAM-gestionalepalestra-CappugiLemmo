package org.example.swamcappugilemmo.BusinessLogic.Mapper;

import jakarta.enterprise.context.ApplicationScoped;
import org.example.swamcappugilemmo.BusinessLogic.DTO.SubscriptionResponseDTO;
import org.example.swamcappugilemmo.DomainModel.Subscription;

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
}
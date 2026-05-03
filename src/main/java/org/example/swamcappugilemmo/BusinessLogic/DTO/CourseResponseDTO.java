package org.example.swamcappugilemmo.BusinessLogic.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseResponseDTO {
    private Long id;
    private String name;
    private int numMembers;
    private int numMax;
    private Long idPersonalTrainer;
}

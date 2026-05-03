package org.example.swamcappugilemmo.BusinessLogic.ControllerLayer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseRequestDTO;
import org.example.swamcappugilemmo.BusinessLogic.DTO.ExerciseResponseDTO;
import org.example.swamcappugilemmo.BusinessLogic.Mapper.ExerciseMapper;
import org.example.swamcappugilemmo.DAO.ExerciseDAO;
import org.example.swamcappugilemmo.DomainModel.Exercise;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExerciseController {

    @Inject
    private ExerciseDAO eDAO;
    @Inject
    private ExerciseMapper eMapper;

    @Transactional
    public ExerciseResponseDTO createExercise(ExerciseRequestDTO request){
        Exercise e = eMapper.toEntity(request);
        eDAO.createExercise(e);
        return eMapper.toDTO(e);
    }

    @Transactional
    public List<ExerciseResponseDTO> getAllExercises(){
        return eDAO.getAllExercises()
                .stream()
                .map(eMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExerciseResponseDTO getExerciseById(Long id){
        return eMapper.toDTO(eDAO.getExerciseById(id));
    }

    @Transactional
    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO request){
        Exercise e = eDAO.getExerciseById(id);
        e.setName(request.getName());
        e.setDescription(request.getDescription());
        Exercise updatedExercise = eDAO.updateExercise(e);
        return eMapper.toDTO(updatedExercise);
    }

    @Transactional
    public ExerciseResponseDTO deleteExercise(Long id){
        return eMapper.toDTO(eDAO.deleteExercise(id));
    }

}

package br.com.keven.spring_boot_essentials.service;

import br.com.keven.spring_boot_essentials.Repository.IExerciciosRepository;
import br.com.keven.spring_boot_essentials.database.model.ExerciciosEntity;
import br.com.keven.spring_boot_essentials.dto.ExercicioDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class  ExerciciosService {
    private final IExerciciosRepository exerciciosRepository;

    public List<ExerciciosEntity> findAll(){
        return exerciciosRepository.findAll();
    }
    public void save(ExercicioDto exercicioDto){
        ExerciciosEntity exercicio = exerciciosRepository.save(ExerciciosEntity.builder()
                        .nome(exercicioDto.getNome())
                        .grupoMuscular(exercicioDto.getGrupoMuscular())
                .build());
        exerciciosRepository.save(exercicio);
    }
    public List<ExerciciosEntity> getExercicioByGrupoMuscular(String grupoMuscular){
        return exerciciosRepository.findAllByGrupoMuscular(grupoMuscular);
    }
}

package br.com.keven.spring_boot_essentials.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ExercicioDto {
    @NotBlank
    private String nome;
    @NotBlank
    private String grupoMuscular;
}

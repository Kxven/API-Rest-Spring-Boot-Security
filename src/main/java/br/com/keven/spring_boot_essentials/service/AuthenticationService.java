package br.com.keven.spring_boot_essentials.service;

import br.com.keven.spring_boot_essentials.Repository.IAlunosRepository;
import br.com.keven.spring_boot_essentials.Repository.IRolesRepository;
import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import br.com.keven.spring_boot_essentials.database.model.RolesEntity;
import br.com.keven.spring_boot_essentials.dto.AlunoDto;
import br.com.keven.spring_boot_essentials.dto.RegisterRequesteDto;
import br.com.keven.spring_boot_essentials.enums.RoleTypeEnum;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IAlunosRepository alunosRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public void criarAluno(RegisterRequesteDto dto) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (aluno != null) {
            throw new BadRequestException("Email já cadastrado");
        }


        RolesEntity role = rolesRepository.findByNome(RoleTypeEnum.ALUNO.name())
                .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                        .nome(RoleTypeEnum.ALUNO.name())
                        .build()
                ));

        alunosRepository.save(AlunosEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .roles(Set.of(role))
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build()
        );

    }
}
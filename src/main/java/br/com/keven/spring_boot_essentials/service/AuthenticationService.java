package br.com.keven.spring_boot_essentials.service;

import br.com.keven.spring_boot_essentials.Repository.IAlunosRepository;
import br.com.keven.spring_boot_essentials.Repository.IRolesRepository;
import br.com.keven.spring_boot_essentials.config.TokenProvider;
import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import br.com.keven.spring_boot_essentials.database.model.RolesEntity;
import br.com.keven.spring_boot_essentials.dto.AlunoDto;
import br.com.keven.spring_boot_essentials.dto.LoginRequesteDto;
import br.com.keven.spring_boot_essentials.dto.RegisterRequesteDto;
import br.com.keven.spring_boot_essentials.dto.TokenResponseDTO;
import br.com.keven.spring_boot_essentials.enums.RoleTypeEnum;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final IAlunosRepository alunosRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${jwt.expiration}")
    private long expirationTime;

    public void register(RegisterRequesteDto dto) throws BadRequestException {
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
    public TokenResponseDTO login(LoginRequesteDto dto) throws Exception{
        try{
            //authentication provider -> userdetailsService -> passwordEncoder.matches()-> autenticado
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
            String token = tokenProvider.gerarToken(authentication);

            return new TokenResponseDTO(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Credenciais inválidaas");
        } catch (Exception e) {
            throw e;
        }
    }

}
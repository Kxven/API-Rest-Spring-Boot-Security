package br.com.keven.spring_boot_essentials.controller;

import br.com.keven.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.keven.spring_boot_essentials.dto.AlunoDto;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import br.com.keven.spring_boot_essentials.exception.NotFoundException;
import br.com.keven.spring_boot_essentials.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoControler {

    private final AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAluno(@Valid @RequestBody AlunoDto alunoDto) throws BadRequestException {
        alunoService.criarAluno(alunoDto);
    }

    @PreAuthorize("#alunoId == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/{alunoId}/avaliacao")
    public AvaliacoesFisicasEntity getAvalicaoFisica(@PathVariable Integer alunoId) throws NotFoundException {
        return alunoService.getAlunoAvaliacao(alunoId);
    }


    @DeleteMapping("/{alunoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAluno(@PathVariable Integer alunoId) throws NotFoundException, BadRequestException{
        alunoService.deletarAluno(alunoId);
    }
}

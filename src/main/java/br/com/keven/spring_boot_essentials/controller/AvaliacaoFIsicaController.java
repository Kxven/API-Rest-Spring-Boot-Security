package br.com.keven.spring_boot_essentials.controller;

import br.com.keven.spring_boot_essentials.dto.AvaliacaoFisicaDto;
import br.com.keven.spring_boot_essentials.dto.AvaliacaoFisicaProjection;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import br.com.keven.spring_boot_essentials.exception.NotFoundException;
import br.com.keven.spring_boot_essentials.service.AvalicaoFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/avaliacoes")
@RequiredArgsConstructor
public class AvaliacaoFIsicaController {
    private final AvalicaoFisicaService avalicaoFisicaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarAvaliacaoFisica(@Valid @RequestBody AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
        avalicaoFisicaService.criarAvaliacaoFisicaa(avaliacaoFisicaDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AvaliacaoFisicaProjection> getAllAvaliacoes(){
        return avalicaoFisicaService.getAllAvaliacaoes();
    }

    @GetMapping("/page/{page}/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Page<AvaliacaoFisicaProjection> getAllAvaliacoesPageable(@PathVariable Integer page,
                                                                    @PathVariable Integer size){
        return avalicaoFisicaService.getAllAvaliacoesPageable(page, size);
    }
}

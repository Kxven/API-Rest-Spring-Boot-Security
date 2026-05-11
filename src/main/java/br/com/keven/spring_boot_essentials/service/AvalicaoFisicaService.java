package br.com.keven.spring_boot_essentials.service;

import br.com.keven.spring_boot_essentials.Repository.IAlunosRepository;
import br.com.keven.spring_boot_essentials.Repository.IAvaliacoesFisicasRepository;
import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import br.com.keven.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.keven.spring_boot_essentials.dto.AvaliacaoFisicaDto;
import br.com.keven.spring_boot_essentials.dto.AvaliacaoFisicaProjection;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import br.com.keven.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvalicaoFisicaService {

    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;

    public void criarAvaliacaoFisicaa(AvaliacaoFisicaDto avaliacaoFisicaDto) throws NotFoundException, BadRequestException {
       AlunosEntity aluno = alunosRepository.findById(avaliacaoFisicaDto.getAlunoId())
               .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        AvaliacoesFisicasEntity avaliacoesFisicas = aluno.getAvaliacoesFisicas();
        if(avaliacoesFisicas != null){
            throw new BadRequestException("Avaliação física já cadastrada para este aluno");
        }

        avaliacoesFisicas = AvaliacoesFisicasEntity.builder()
                .peso(avaliacaoFisicaDto.getPeso())
                .altura(avaliacaoFisicaDto.getAltura())
                .porcentagemGorduraCorporal(avaliacaoFisicaDto.getPorcentagemGorduraCorporal())
                .build();

        //O hibernate vai fazer a inserção automaticamente por conta do CascadeType.All que vai salvar automaticamente pelo relacionamento da tabela "alunos"
        //avaliacoesFisicas = avaliacoesFisicasRepository.save(avaliacoesFisicas);

        aluno.setAvaliacoesFisicas(avaliacoesFisicas);
        alunosRepository.save(aluno);
    }
    public List<AvaliacaoFisicaProjection> getAllAvaliacaoes(){
        return avaliacoesFisicasRepository.getAllAvaliacoes();
    }

    public Page<AvaliacaoFisicaProjection> getAllAvaliacoesPageable(Integer page, Integer size){
        return avaliacoesFisicasRepository.getAllAvaliacoesPage(PageRequest.of(page, size));
    }

}

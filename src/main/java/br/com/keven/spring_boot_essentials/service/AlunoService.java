package br.com.keven.spring_boot_essentials.service;

import br.com.keven.spring_boot_essentials.Repository.IAlunosRepository;
import br.com.keven.spring_boot_essentials.Repository.IAvaliacoesFisicasRepository;
import br.com.keven.spring_boot_essentials.Repository.ITreinosRepository;
import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import br.com.keven.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.keven.spring_boot_essentials.database.model.TreinosEntity;
import br.com.keven.spring_boot_essentials.dto.AlunoDto;
import br.com.keven.spring_boot_essentials.exception.BadRequestException;
import br.com.keven.spring_boot_essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final IAlunosRepository alunosRepository;
    private final IAvaliacoesFisicasRepository avaliacoesFisicasRepository;
    private final ITreinosRepository treinosRepository;

    public void criarAluno(AlunoDto alunoDTO) throws BadRequestException {
        AlunosEntity aluno = alunosRepository.findByEmail(alunoDTO.getEmail())
                .orElse(null);

        if (aluno != null){
            throw new BadRequestException("Email já cadastrado");
        }

        alunosRepository.save(AlunosEntity.builder()
                        .nome(alunoDTO.getNome())
                        .email(alunoDTO.getEmail())
                .build());
    }
    public AvaliacoesFisicasEntity getAlunoAvaliacao(Integer alunoId) throws NotFoundException{
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        AvaliacoesFisicasEntity avaliacao = aluno.getAvaliacoesFisicas();
        if (avaliacao == null){
            throw new NotFoundException("Avaliação fisica não encontrada para este aluno");
        }
        return avaliacao;
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletarAluno(Integer alunoId) throws NotFoundException, BadRequestException{
        AlunosEntity aluno = alunosRepository.findById(alunoId)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));
        //1 deletar treino do aluno

        List<Integer>  treinoAlunosId = aluno.getTreino().stream()
                .map(TreinosEntity::getId)
                .toList();
        treinosRepository.deleteAllById(treinoAlunosId);
        //2 deletar aluno
        alunosRepository.deleteById(alunoId);

        //3 deletar avaliacao fisica desse aluno
        avaliacoesFisicasRepository.deleteById(aluno.getAvaliacoesFisicas().getId());
    }

}

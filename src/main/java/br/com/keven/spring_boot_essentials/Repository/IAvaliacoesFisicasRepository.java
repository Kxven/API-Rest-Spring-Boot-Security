package br.com.keven.spring_boot_essentials.Repository;

import br.com.keven.spring_boot_essentials.database.model.AvaliacoesFisicasEntity;
import br.com.keven.spring_boot_essentials.dto.AvaliacaoFisicaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAvaliacoesFisicasRepository extends JpaRepository<AvaliacoesFisicasEntity, Integer> {

    @NativeQuery(value = """
        SELECT a.id                             idAluno,
               a.nome                           nomeAluno,
               af.id                            idAvaliacao, 
               af.peso                          peso,
               af.altura                        altura,
               af.percentual_gordura_corporal   percentualGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
        """)
    List<AvaliacaoFisicaProjection> getAllAvaliacoes();


    @Query(value = """
        SELECT a.id                             idAluno,
            a.nome                           nomeAluno,
            af.id                            idAvaliacao, 
            af.peso                          peso,
            af.altura                        altura,
            af.percentual_gordura_corporal   percentualGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
        """,
    countQuery = """
            SELECT count(af.id)
            FROM avaliacoes_fisicas af
            INNER JOIN alunos a
            ON a.avaliacao_fisica_id = af.id
            """,nativeQuery = true)
    Page<AvaliacaoFisicaProjection> getAllAvaliacoesPage(Pageable pageable);
}

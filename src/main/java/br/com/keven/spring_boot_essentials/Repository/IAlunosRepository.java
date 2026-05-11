package br.com.keven.spring_boot_essentials.Repository;

import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IAlunosRepository extends JpaRepository<AlunosEntity, Integer> {

    Optional<AlunosEntity> findByEmail(String email);

    //@Query(value = "SELECT a FROM AlunosEntity a JOIN FETCH a.avaliacaoFisica WHERE a.id = :alunoId")
    //Optional<AlunosEntity> findByFetch();
}

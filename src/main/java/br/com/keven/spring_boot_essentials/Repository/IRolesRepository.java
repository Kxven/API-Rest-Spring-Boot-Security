package br.com.keven.spring_boot_essentials.Repository;

import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import br.com.keven.spring_boot_essentials.database.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolesRepository extends JpaRepository<RolesEntity, Integer> {

    Optional<RolesEntity> findByNome(String role);
}

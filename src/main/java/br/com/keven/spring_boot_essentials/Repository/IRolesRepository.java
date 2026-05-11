package br.com.keven.spring_boot_essentials.Repository;

import br.com.keven.spring_boot_essentials.database.model.AlunosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolesRepository extends JpaRepository<AlunosEntity, Integer> {

}

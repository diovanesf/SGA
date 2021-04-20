package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Exame;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data SQL repository for the Exame entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    public List<Exame> findByAmostraId(Long amostraId);
}

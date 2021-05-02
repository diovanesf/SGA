package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Vacina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Vacina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {}

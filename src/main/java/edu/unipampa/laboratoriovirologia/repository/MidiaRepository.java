package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Midia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Midia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MidiaRepository extends JpaRepository<Midia, Long> {}

package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Amostra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Amostra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, Long> {}

package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Subamostra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Subamostra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubamostraRepository extends JpaRepository<Subamostra, Long> {}

package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Medicoveterinario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Medicoveterinario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicoveterinarioRepository extends JpaRepository<Medicoveterinario, Long>, JpaSpecificationExecutor<Medicoveterinario> {}

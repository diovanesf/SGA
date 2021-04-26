package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Proprietario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Proprietario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long>, JpaSpecificationExecutor<Proprietario> {}

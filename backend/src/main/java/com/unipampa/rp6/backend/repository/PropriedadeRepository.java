package com.unipampa.rp6.backend.repository;

import com.unipampa.rp6.backend.domain.Propriedade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Propriedade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {}

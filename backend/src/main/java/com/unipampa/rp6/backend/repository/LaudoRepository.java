package com.unipampa.rp6.backend.repository;

import com.unipampa.rp6.backend.domain.Laudo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Laudo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaudoRepository extends JpaRepository<Laudo, Long> {}

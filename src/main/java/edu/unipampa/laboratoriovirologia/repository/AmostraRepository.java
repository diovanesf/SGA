package edu.unipampa.laboratoriovirologia.repository;

import edu.unipampa.laboratoriovirologia.domain.Amostra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Amostra entity.
 */
@Repository
public interface AmostraRepository extends JpaRepository<Amostra, Long> {
    @Query(
        value = "select distinct amostra from Amostra amostra left join fetch amostra.users",
        countQuery = "select count(distinct amostra) from Amostra amostra"
    )
    Page<Amostra> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct amostra from Amostra amostra left join fetch amostra.users")
    List<Amostra> findAllWithEagerRelationships();

    @Query("select amostra from Amostra amostra left join fetch amostra.users where amostra.id =:id")
    Optional<Amostra> findOneWithEagerRelationships(@Param("id") Long id);
}

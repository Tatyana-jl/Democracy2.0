package com.junction.repository;
import com.junction.domain.ProblemType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProblemType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProblemTypeRepository extends JpaRepository<ProblemType, Long> {

}

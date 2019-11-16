package com.junction.repository;
import com.junction.domain.RuzLegalForm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RuzLegalForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuzLegalFormRepository extends JpaRepository<RuzLegalForm, Long> {

}

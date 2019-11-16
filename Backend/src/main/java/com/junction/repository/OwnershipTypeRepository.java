package com.junction.repository;
import com.junction.domain.OwnershipType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OwnershipType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OwnershipTypeRepository extends JpaRepository<OwnershipType, Long> {

}

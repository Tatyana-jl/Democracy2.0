package com.junction.repository;
import com.junction.domain.OrganizationSize;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrganizationSize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationSizeRepository extends JpaRepository<OrganizationSize, Long> {

}

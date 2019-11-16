package com.junction.repository;
import com.junction.domain.RuzAttachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the RuzAttachment entity.
 */
@Repository
public interface RuzAttachmentRepository extends JpaRepository<RuzAttachment, Long> {

    @Query(value = "select distinct ruzAttachment from RuzAttachment ruzAttachment left join fetch ruzAttachment.financeReports",
        countQuery = "select count(distinct ruzAttachment) from RuzAttachment ruzAttachment")
    Page<RuzAttachment> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct ruzAttachment from RuzAttachment ruzAttachment left join fetch ruzAttachment.financeReports")
    List<RuzAttachment> findAllWithEagerRelationships();

    @Query("select ruzAttachment from RuzAttachment ruzAttachment left join fetch ruzAttachment.financeReports where ruzAttachment.id =:id")
    Optional<RuzAttachment> findOneWithEagerRelationships(@Param("id") Long id);

}

package com.junction.repository;
import com.junction.domain.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the AdminUser entity.
 */
@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    @Query(value = "select distinct adminUser from AdminUser adminUser left join fetch adminUser.problems",
        countQuery = "select count(distinct adminUser) from AdminUser adminUser")
    Page<AdminUser> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct adminUser from AdminUser adminUser left join fetch adminUser.problems")
    List<AdminUser> findAllWithEagerRelationships();

    @Query("select adminUser from AdminUser adminUser left join fetch adminUser.problems where adminUser.id =:id")
    Optional<AdminUser> findOneWithEagerRelationships(@Param("id") Long id);

}

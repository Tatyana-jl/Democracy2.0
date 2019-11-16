package com.junction.repository;
import com.junction.domain.SkNaceCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SkNaceCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkNaceCategoryRepository extends JpaRepository<SkNaceCategory, Long> {

}

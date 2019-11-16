package com.junction.repository;
import com.junction.domain.Template;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Template entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

}

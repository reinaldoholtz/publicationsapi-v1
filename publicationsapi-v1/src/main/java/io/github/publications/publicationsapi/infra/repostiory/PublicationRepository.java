package io.github.publications.publicationsapi.infra.repostiory;

import io.github.publications.publicationsapi.domain.entity.Publication;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.util.StringUtils;
import java.util.List;
import org.springframework.data.repository.query.Param;

import static io.github.publications.publicationsapi.infra.repostiory.specs.GenericSpecs.conjunction;
import static io.github.publications.publicationsapi.infra.repostiory.specs.PublicationSpecs.*;
import static org.springframework.data.jpa.domain.Specification.anyOf;
import static org.springframework.data.jpa.domain.Specification.where;

public interface PublicationRepository extends JpaRepository <Publication,String>, JpaSpecificationExecutor<Publication> {

    @Query(value = "SELECT * FROM publicacao p WHERE documento @@ publicacao_tsquery(:query)", nativeQuery = true)
    List<Publication> findByDocumentTsQuery(@Param("query") String query);

    default List<Publication> findByDescriptionLike(String query){
        // SELECT * FROM publication WHERE 1 = 1
        Specification<Publication> spec = where(conjunction());

        if (StringUtils.hasText(query)){
            spec = spec.and(anyOf(descriptionLike(query)));
        }
        return findAll(spec);
    }
}

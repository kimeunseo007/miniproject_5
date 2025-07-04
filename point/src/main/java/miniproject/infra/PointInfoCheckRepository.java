package miniproject.infra;

import java.util.List;
import miniproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "pointInfoChecks",
    path = "pointInfoChecks"
)
public interface PointInfoCheckRepository
    extends PagingAndSortingRepository<PointInfoCheck, Long> {
        Optional<PointInfoCheck> findByUserId(Long userId);
    }

package miniproject.infra;

import java.util.Optional;
import miniproject.domain.SubscriptionStatusCheck;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "subscriptionStatusChecks",
    path = "subscriptionStatusChecks"
)
public interface SubscriptionStatusCheckRepository
    extends PagingAndSortingRepository<SubscriptionStatusCheck, Long> {

    Optional<SubscriptionStatusCheck> findByUserId(Long userId);
}

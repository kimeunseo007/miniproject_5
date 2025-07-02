package miniproject.infra;

import java.util.List;
import miniproject.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Optional;

@RepositoryRestResource(
    collectionResourceRel = "bestSellerLists",
    path = "bestSellerLists"
)
public interface BestSellerListRepository
    extends PagingAndSortingRepository<BestSellerList, Long> {
        Optional<BestSellerList> findByBookId(Long bookId);
    }

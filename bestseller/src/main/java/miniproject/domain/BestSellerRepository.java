package miniproject.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(
    collectionResourceRel = "bestSellers",
    path = "bestSellers"
)
public interface BestSellerRepository
    extends PagingAndSortingRepository<BestSeller, Long> {

    Optional<BestSeller> findByBookId(Long bookId);

    // ✅ 조회수 5 이상 + 베스트셀러 선정된 책만
    List<BestSeller> findByViewCountGreaterThanEqualAndSelectedStatus(Integer viewCount, String selectedStatus);
}

package miniproject.infra;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import miniproject.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional
public class BestSellerController {

    @Autowired
    BestSellerRepository bestSellerRepository;

    @RequestMapping(
        value = "/bestSellers/{id}/increasebookview",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public BestSeller increaseBookView(
        @PathVariable(value = "id") Long id,
        @RequestBody IncreaseBookViewCommand increaseBookViewCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /bestSeller/increaseBookView  called #####");
        Optional<BestSeller> optionalBestSeller = bestSellerRepository.findById(id);

        optionalBestSeller.orElseThrow(() -> new Exception("No Entity Found"));
        BestSeller bestSeller = optionalBestSeller.get();
        bestSeller.increaseBookView(increaseBookViewCommand);

        bestSellerRepository.save(bestSeller);
        return bestSeller;
    }

    @RequestMapping(
        value = "/bestSellers/{id}/selectbestseller",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public BestSeller selectBestSeller(
        @PathVariable(value = "id") Long id,
        @RequestBody SelectBestSellerCommand selectBestSellerCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /bestSeller/selectBestSeller  called #####");
        Optional<BestSeller> optionalBestSeller = bestSellerRepository.findById(id);

        optionalBestSeller.orElseThrow(() -> new Exception("No Entity Found"));
        BestSeller bestSeller = optionalBestSeller.get();
        bestSeller.selectBestSeller(selectBestSellerCommand);

        bestSellerRepository.save(bestSeller);
        return bestSeller;
    }

    // ✅ 추가: 조회수 5 이상 + 베스트셀러만 조회
    @GetMapping("/bestSellers/viewover5")
    public List<BestSeller> getBooksViewOver5() {
        return bestSellerRepository.findByViewCountGreaterThanEqualAndSelectedStatus(5, "베스트셀러");
    }
}

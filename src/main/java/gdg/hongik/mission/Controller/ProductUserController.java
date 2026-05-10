package gdg.hongik.mission.Controller;

import gdg.hongik.mission.Entity.Product;
import gdg.hongik.mission.Repository.ProductRepository;
import gdg.hongik.mission.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductUserController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    // 상품 조회
    @GetMapping
    public Product getProduct(@RequestParam String name) {

        return productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("상품 없음"));
    }

    // 상품 구매
    @PostMapping("/purchase")
    public String purchase(@RequestBody List<Product> requests) {
        // 복잡한 재고 확인, 계산, 문자열 생성은 모두 서비스가 처리합니다.
        return productService.purchaseProducts(requests);
    }
}

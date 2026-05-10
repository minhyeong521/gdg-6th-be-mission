package gdg.hongik.mission.Controller;

import gdg.hongik.mission.Entity.Product;
import gdg.hongik.mission.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductAdminController {
    private final ProductService productService;

    // 1. 상품 등록
    @PostMapping
    public String createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return "상품 등록 완료";
    }

    // 2. 재고 추가
    @PatchMapping("/stock")
    public String addStock(@RequestBody Product request) {
        return productService.addStock(request.getId(), request.getQuantity());
    }

    // 3. 상품 삭제
    @DeleteMapping
    public List<Product> deleteProducts(@RequestBody List<Long> ids) {
        return productService.deleteProducts(ids);
    }
}

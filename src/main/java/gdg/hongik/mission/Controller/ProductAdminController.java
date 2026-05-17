package gdg.hongik.mission.Controller;

import gdg.hongik.mission.Dto.AddStockRequest;
import gdg.hongik.mission.Dto.CreateProductRequest;
import gdg.hongik.mission.Dto.GetProductResponse;
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
    public String createProduct(@RequestBody CreateProductRequest request) {
        productService.createProduct(request);
        return "상품 등록 완료";
    }

    // 2. 재고 추가
    @PatchMapping("/stock")
    public String addStock(@RequestBody AddStockRequest request) {
        return productService.addStock(request);
    }

    // 3. 상품 삭제
    @DeleteMapping
    // <GetProductResponse>dto를 사용해서 외부에 보여주는 정보를 제한한다
    public List<GetProductResponse> deleteProducts(@RequestBody List<Long> ids) {

        return productService.deleteProducts(ids);
    }
}

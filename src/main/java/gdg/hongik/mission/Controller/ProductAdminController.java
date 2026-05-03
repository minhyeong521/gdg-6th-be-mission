package gdg.hongik.mission.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;

import java.util.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
public class ProductAdminController {

    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody Map<String, Object> request) {
        String name = String.valueOf(request.get("name"));
        int price = Integer.parseInt(String.valueOf(request.get("price")));
        int stock = Integer.parseInt(String.valueOf(request.get("stock")));

        // 중복 체크
        for (Product p : ProductStore.products) {
            if (p.getName().equals(name)) {
                throw new RuntimeException("이미 등록된 상품명입니다.");
            }
        }

        Product newProduct = new Product(ProductStore.sequence++, name, price, stock);
        ProductStore.products.add(newProduct);
        return ResponseEntity.status(201).build();
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Object> addStock(
            @PathVariable Long productId,
            @RequestBody Map<String, Object> request) {
        
        int stockPlus = Integer.parseInt(String.valueOf(request.get("stockPlus")));

        for (Product p : ProductStore.products) {
            if (p.getProductId().equals(productId)) {
                p.setStock(p.getStock() + stockPlus);

                Map<String, Object> response = new HashMap<>();
                response.put("name", p.getName());
                response.put("stock", p.getStock());
                return ResponseEntity.ok(response);
            }
        }
        throw new RuntimeException("해당 상품을 찾을 수 없습니다.");
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteProducts(@RequestBody Map<String, Object> request) {
        // "deleteList": [1, 2] 형태를 처리
        List<Integer> deleteIds = (List<Integer>) request.get("deleteList");

        // 삭제 처리 (Integer를 Long으로 비교)
        ProductStore.products.removeIf(p -> 
            deleteIds.stream().anyMatch(id -> Long.valueOf(id).equals(p.getProductId()))
        );

        // 남은 목록 응답 구성
        List<Map<String, Object>> remainList = new ArrayList<>();
        for (Product p : ProductStore.products) {
            Map<String, Object> info = new HashMap<>();
            info.put("name", p.getName());
            info.put("stock", p.getStock());
            remainList.add(info);
        }

        return ResponseEntity.ok(remainList);
    }
}

package gdg.hongik.mission.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gdg.hongik.mission.Product;
import gdg.hongik.mission.ProductStore;
import lombok.RequiredArgsConstructor;
import java.util.*;

@RestController
@RequestMapping("/products")
public class ProductUserController {
    
    @GetMapping
    public ResponseEntity getProduct(@RequestParam String name){
        for (Product p : ProductStore.products) {
            if (p.getName().equals(name)) {
                return ResponseEntity.ok(p);
            }
        }
        throw new RuntimeException("해당 상품을 찾을 수 없습니다. ");
    }
    

    @PostMapping("/orders")
    public ResponseEntity<Object> buyProducts(@RequestBody List<Object> requests) {
        int totalPrice = 0;
        List<Map<String, Object>> ordersList = new ArrayList<>();

        for (Object obj : requests) {
            // Object를 Map으로 형변환하여 내부 데이터에 접근
            Map<String, Object> item = (Map<String, Object>) obj;
            
            // JSON 숫자는 보통 Integer로 넘어오므로 안전하게 변환
            Long productId = Long.valueOf(String.valueOf(item.get("productId")));
            int quantity = Integer.parseInt(String.valueOf(item.get("quantity")));

            Product foundProduct = null;
            for (Product p : ProductStore.products) {
                if (p.getProductId().equals(productId)) {
                    foundProduct = p;
                    break;
                }
            }

            // 예외 처리: 상품 없음 또는 재고 부족
            if (foundProduct == null) {
                throw new RuntimeException("ID " + productId + " 상품을 찾을 수 없습니다.");
            }
            if (foundProduct.getStock() < quantity) {
                throw new RuntimeException(foundProduct.getName() + "의 재고가 부족합니다.");
            }

            // 비즈니스 로직
            int itemAmount = foundProduct.getPrice() * quantity;
            totalPrice += itemAmount;
            foundProduct.setStock(foundProduct.getStock() - quantity);

            // 응답용 데이터 생성
            Map<String, Object> detail = new HashMap<>();
            detail.put("name", foundProduct.getName());
            detail.put("quantity", quantity);
            detail.put("price", itemAmount);
            ordersList.add(detail);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalPrice", totalPrice);
        result.put("ordersList", ordersList);

        return ResponseEntity.status(201).body(result);
    }
}

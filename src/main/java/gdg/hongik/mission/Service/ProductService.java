package gdg.hongik.mission.Service;

import gdg.hongik.mission.Entity.Product;
import gdg.hongik.mission.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public String purchaseProducts(List<Product> requests){

        int totalPrice = 0;
        String result = "";

        for (Product request : requests) {
            // 상품 찾기
            Product found = productRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            // 재고 확인
            if (found.getStock() < request.getQuantity()) {
                throw new RuntimeException(found.getName() + " 재고 부족");
            }

            // 재고 감소
            found.setStock(found.getStock() - request.getQuantity());

            int price = found.getPrice() * request.getQuantity();
            totalPrice += price;

            result += found.getName() + " "
                    + request.getQuantity() + "개 구매 ("
                    + price + "원)\n";
        }

        result += "총 금액: " + totalPrice;
        return result;
    }

    // 상품 등록
    @Transactional
    public void createProduct(Product product) {

        productRepository.findByName(product.getName())
                .ifPresent(p -> {
                    throw new RuntimeException("이미 존재하는 상품입니다.");
                });

        productRepository.save(product);
    }

    // 재고 추가
    @Transactional
    public String addStock(Long productId, int quantity) {
        Product found = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        found.setStock(found.getStock() + quantity);

        return found.getName() + " 재고: " + found.getStock();
    }

    // 상품 삭제
    @Transactional
    public List<Product> deleteProducts(List<Long> ids) {
        // 복잡한 for문 대신 JPA에서 제공하는 삭제 기능을 사용
        productRepository.deleteAllByIdInBatch(ids);

        // 삭제 후 남은 전체 목록 반환
        return productRepository.findAll();
    }
}

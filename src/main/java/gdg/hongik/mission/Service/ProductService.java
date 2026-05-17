package gdg.hongik.mission.Service;

import gdg.hongik.mission.Dto.AddStockRequest;
import gdg.hongik.mission.Dto.CreateProductRequest;
import gdg.hongik.mission.Dto.GetProductResponse;
import gdg.hongik.mission.Dto.PurchaseRequest;
import gdg.hongik.mission.Entity.Product;
import gdg.hongik.mission.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //상품 조회
    @Transactional(readOnly = true)
    public GetProductResponse getProduct(String name){

        Product found = productRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        return new GetProductResponse(found);
    }

    // 상품 구매
    @Transactional
    public String purchaseProducts(List<PurchaseRequest> requests){

        int totalPrice = 0;
        String result = "";

        for (PurchaseRequest request : requests) {
            // 상품 찾기
            Product found = productRepository.findById(request.id())
                    .orElseThrow(() -> new RuntimeException("상품 없음"));

            // 재고 확인
            if (found.getStock() < request.quantity()) {
                throw new RuntimeException(found.getName() + " 재고 부족");
            }

            // 재고 감소
            found.setStock(found.getStock() - request.quantity());

            int price = found.getPrice() * request.quantity();
            totalPrice += price;

            result += found.getName() + " "
                    + request.quantity() + "개 구매 ("
                    + price + "원)\n";
        }

        result += "총 금액: " + totalPrice;
        return result;
    }

    // 상품 등록
    @Transactional
    public void createProduct(CreateProductRequest request) {

        productRepository.findByName(request.name())
                .ifPresent(p -> {
                    throw new RuntimeException("이미 존재하는 상품입니다.");
                });

        // DTO 상자는 저장이 안되서 엔티티에 이 값을 넣어줘야 한다.
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());

        productRepository.save(product);
    }

    // 재고 추가
    @Transactional
    public String addStock(AddStockRequest request) {
        Product found = productRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("상품 없음"));

        found.setStock(found.getStock() + request.quantity());

        return found.getName() + " 재고: " + found.getStock();
    }

    // 상품 삭제
    @Transactional
    public List<GetProductResponse> deleteProducts(List<Long> ids) {
        // 복잡한 for문 대신 JPA에서 제공하는 삭제 기능을 사용
        productRepository.deleteAllByIdInBatch(ids);

        List<Product> products = productRepository.findAll();

        List<GetProductResponse> result = new ArrayList<>();

        for(Product p: products){
            GetProductResponse productResponse = new GetProductResponse(p);
            result.add(productResponse);
        }

        return result;
    }
}

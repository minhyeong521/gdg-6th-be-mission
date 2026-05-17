package gdg.hongik.mission.Dto;

import gdg.hongik.mission.Entity.Product;

// 상품 조회 응답
public record GetProductResponse(Long id, String name, int price, int stock){

    public GetProductResponse(Product product){
        this(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}

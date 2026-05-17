package gdg.hongik.mission.Dto;

import java.util.List;

// 상품 삭제 요청
public record DeleteProductsRequest(List<Long> ids) {
}

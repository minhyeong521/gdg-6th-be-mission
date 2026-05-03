package gdg.hongik.mission;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Long productId;
    private String name;
    private int price;
    private int stock;

    public Product(Long productId, String name, int price, int stock)
    {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    
}

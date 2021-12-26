package kitchenpos.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;

    private ProductResponse(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPriceValue());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ProductResponse))
            return false;
        ProductResponse that = (ProductResponse)o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}

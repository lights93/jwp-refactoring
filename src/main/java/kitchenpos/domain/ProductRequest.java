package kitchenpos.domain;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private BigDecimal price;

    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public Product toEntity() {
        return new Product(name, price);
    }
}
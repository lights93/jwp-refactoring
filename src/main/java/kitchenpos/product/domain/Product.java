package kitchenpos.product.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kitchenpos.common.price.domain.Price;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Embedded
    private Price price;

    protected Product() {
    }

    public Product(String name, BigDecimal price) {
        this(null, name, new Price(price));
    }

    public Product(String name, Price price) {
        this(null, name, price);
    }

    public Product(Long id, String name, BigDecimal price) {
        this(id, name, new Price(price));
    }

    public Product(Long id, String name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public BigDecimal getPriceValue() {
        return price.getPrice();
    }
}

package kitchenpos.menu.application;

import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuProducts;
import kitchenpos.menu.dto.MenuResponse;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuService(
            MenuRepository menuRepository,
            MenuGroupRepository menuGroupRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    public MenuResponse create(MenuRequest menuRequest) {
        Menu menu = new Menu(
                menuRequest.getName(),
                menuRequest.getPrice(),
                menuRequest.getMenuGroupId()
        );

        menu.validatePrice();

        if (!menuGroupRepository.existsById(menu.getMenuGroupId())) {
            throw new IllegalArgumentException();
        }

        final Menu savedMenu = menuRepository.save(menu);

        final List<MenuProduct> menuProductList =
                menuRequest.getMenuProducts().stream().map(menuProductRequest -> new MenuProduct(
                        menu,
                        productRepository.findById(menuProductRequest.getProductId()).orElseThrow(NoSuchElementException::new),
                        menuProductRequest.getQuantity()
                )).collect(Collectors.toList());

        MenuProducts menuProducts = new MenuProducts(menuProductList);

        BigDecimal sum = menuProducts.sumOfMenuProducts();

        menu.compareMenuPriceToProductsSum(sum);

        savedMenu.addMenuProducts(menuProducts);

        return MenuResponse.of(savedMenu);
    }

    public List<MenuResponse> list() {
        final List<Menu> menus = menuRepository.findAll();

        return menus.stream().map(MenuResponse::of).collect(Collectors.toList());
    }
}
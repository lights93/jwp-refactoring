package kitchenpos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;

public interface OrderTableDao extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findAllByIdIn(List<Long> ids);

    List<OrderTable> findAllByTableGroup(TableGroup tableGroup);
}

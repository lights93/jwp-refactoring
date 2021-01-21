package kitchenpos.order.domain.repository;

import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.TableGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderTableRepository extends JpaRepository<OrderTable, Long> {
	List<OrderTable> findAllByIdIn(List<Long> orderTableIds);

	List<OrderTable> findAllByTableGroup(TableGroup tableGroup);

}

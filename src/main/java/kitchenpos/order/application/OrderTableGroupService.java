package kitchenpos.order.application;

import kitchenpos.order.domain.OrderTable;
import kitchenpos.order.domain.OrderTables;
import kitchenpos.order.domain.TableGroup;
import kitchenpos.order.domain.repository.OrderTableRepository;
import kitchenpos.order.domain.repository.TableGroupRepository;
import kitchenpos.order.dto.TableGroupRequest;
import kitchenpos.order.dto.TableGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderTableGroupService {
	private final OrderTableRepository orderTableRepository;
	private final TableGroupRepository tableGroupRepository;
	private final OrderTableService orderTableService;

	public OrderTableGroupService(final OrderTableRepository orderTableRepository, final TableGroupRepository tableGroupRepository, OrderTableService orderTableService) {
		this.orderTableRepository = orderTableRepository;
		this.tableGroupRepository = tableGroupRepository;
		this.orderTableService = orderTableService;
	}

	@Transactional
	public TableGroupResponse create(final TableGroupRequest request) {
		List<Long> orderTableIds = new ArrayList<>(request.getOrderTableIds());
		List<OrderTable> orderTableList = orderTableRepository.findAllByIdIn(orderTableIds);

		OrderTables orderTables = new OrderTables(orderTableList, request.getOrderTableIds().size());

		TableGroup savedTableGroup = tableGroupRepository.save(TableGroup.newInstance());
		orderTables.group(savedTableGroup);

		return TableGroupResponse.of(orderTables);
	}

	@Transactional
	public void ungroup(final Long tableGroupId) {
		TableGroup tableGroup = tableGroupRepository.findById(tableGroupId).orElseThrow(() -> new IllegalArgumentException());
		orderTableService.unGroup(tableGroup);
		tableGroupRepository.deleteById(tableGroup.getId());
	}
}

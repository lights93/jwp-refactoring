package kitchenpos.table.domain;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.domain.AbstractAggregateRoot;

import kitchenpos.common.exception.KitchenposErrorCode;
import kitchenpos.common.exception.KitchenposException;

@Entity
public class OrderTable extends AbstractAggregateRoot<OrderTable> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tableGroupId;

    @Embedded
    private NumberOfGuests numberOfGuests;

    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(Long tableGroupId, int numberOfGuests) {
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public OrderTable(int numberOfGuests, boolean empty) {
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = empty;
    }

    public OrderTable(Long id, Long tableGroupId, int numberOfGuests, boolean empty) {
        this.id = id;
        this.tableGroupId = tableGroupId;
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = empty;
    }

    public Long getId() {
        return id;
    }

    public Long getTableGroupId() {
        return tableGroupId;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getGuestNumber() {
        return numberOfGuests.getNumberOfGuests();
    }

    public void updateNumberOfGuests(final NumberOfGuests numberOfGuests) {
        validateNotEmpty();
        this.numberOfGuests = numberOfGuests;
    }

    public void validateNotEmpty() {
        if (empty) {
            throw new KitchenposException(KitchenposErrorCode.TABLE_IS_EMPTY);
        }
    }

    public void updateEmpty(final boolean empty) {
        checkNotGrouped();
        registerEvent(new OrderStatusValidateEvent(id));
        this.empty = empty;
    }

    private void checkNotGrouped() {
        if (Objects.nonNull(tableGroupId)) {
            throw new KitchenposException(KitchenposErrorCode.TABLE_IS_IN_GROUP);
        }
    }

    public boolean cannotBeGrouped() {
        return !empty || Objects.nonNull(tableGroupId);
    }

    public void ungroup() {
        this.tableGroupId = null;
        registerEvent(new OrderStatusValidateEvent(id));
    }

    public void group(Long tableGroupId) {
        this.empty = false;
        this.tableGroupId = tableGroupId;
    }
}
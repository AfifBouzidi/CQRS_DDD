package tn.inf.sales.domain.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.inf.sales.commands.order.AddLineItemCommand;
import tn.inf.sales.commands.order.CancelOrderCommand;
import tn.inf.sales.commands.order.CloseOrderCommand;
import tn.inf.sales.commands.order.CreateOrderCommand;
import tn.inf.sales.commands.order.PlaceOrderCommand;
import tn.inf.sales.commands.order.RemoveLineItemCommand;
import tn.inf.sales.events.order.LineItemAddedEvent;
import tn.inf.sales.events.order.OrderCancledEvent;
import tn.inf.sales.events.order.OrderClosedEvent;
import tn.inf.sales.events.order.OrderCreatedEvent;
import tn.inf.sales.events.order.OrderPlacedEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.LineItemId;
import tn.inf.sales.kernel.customer.OrderId;
import tn.inf.sales.kernel.customer.OrderStatus;
import tn.inf.sales.kernel.customer.ProductId;

@Getter
@NoArgsConstructor
@Entity
@Aggregate(repository = "orderAggregateRepository")
public class OrderAggregate {

	@EmbeddedId
	@AggregateIdentifier
	private OrderId orderId;

	private Date creationDate;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Embedded
	private CustomerId customerId;

	@AggregateMember
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LineItem> items = new ArrayList<>();

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {

		this.orderId = new OrderId(IdentifierFactory.getInstance().generateIdentifier());
		this.creationDate = new Date();
		this.customerId = new CustomerId(createOrderCommand.getCustomerId());
		this.status = OrderStatus.CREATED;
		AggregateLifecycle.apply(new OrderCreatedEvent(this.orderId, this.customerId));
	}

	@CommandHandler
	public void handle(CloseOrderCommand removeLineItemCommand) {
		this.status = OrderStatus.FULFILLED;
		AggregateLifecycle.apply(new OrderClosedEvent(new OrderId(removeLineItemCommand.getOrderId())));
	}

	@CommandHandler
	public void handle(CancelOrderCommand cancelOrderCommand) {
		this.status = OrderStatus.CANCLED;
		AggregateLifecycle.apply(new OrderCancledEvent(new OrderId(cancelOrderCommand.getOrderId())));
	}

	@CommandHandler
	public void handle(AddLineItemCommand addLineItemCommand) {
		LineItem lineItem = new LineItem(new LineItemId(IdentifierFactory.getInstance().generateIdentifier()),
				new ProductId(addLineItemCommand.getProductId()), addLineItemCommand.getQuantity());
		this.items.add(lineItem);
		AggregateLifecycle.apply(new LineItemAddedEvent(this.orderId, lineItem.getItemId(), lineItem.getProductId(),
				lineItem.getQuantity()));
	}

	@CommandHandler
	public void handle(RemoveLineItemCommand removeLineItemCommand) {
		items.removeIf((LineItem item) -> item.getItemId().equals(removeLineItemCommand.getItemId()));
	}

	@CommandHandler
	public void handle(PlaceOrderCommand placeOrderCommand) {
		this.status = OrderStatus.INPROCESS;
		Map<String, Integer> productQuantityMap = items.stream()
				.collect(Collectors.toMap(x -> x.getProductId().getProductId(), x -> x.getQuantity()));
		AggregateLifecycle.apply(new OrderPlacedEvent(this.orderId.getOrderId(), this.customerId.getCustomerId(), productQuantityMap));
	}
}

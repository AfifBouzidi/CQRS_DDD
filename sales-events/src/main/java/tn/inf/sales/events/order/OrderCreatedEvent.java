package tn.inf.sales.events.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.OrderId;

@Getter
@AllArgsConstructor
@ToString
public class OrderCreatedEvent extends AbstractEvent {

	private OrderId orderId;
	
	private CustomerId customerId;
}

package tn.inf.sales.events.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;
import tn.inf.sales.kernel.customer.LineItemId;
import tn.inf.sales.kernel.customer.OrderId;
import tn.inf.sales.kernel.customer.ProductId;

@Getter
@AllArgsConstructor
@ToString
public class LineItemAddedEvent extends AbstractEvent {

	private OrderId orderId;

	private LineItemId lineItemId;

	private ProductId productId;

	private int quantity;

}

package tn.inf.sales.events.order;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.OrderId;

@Getter
@AllArgsConstructor
@ToString
public class OrderPlacedEvent extends AbstractEvent {

	private String orderId;

	private String customerId;
	
	private Map<String,Integer> productQuantityMap;

}

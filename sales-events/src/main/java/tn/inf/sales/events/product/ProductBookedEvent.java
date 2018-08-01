package tn.inf.sales.events.product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@Builder
@ToString
public class ProductBookedEvent extends AbstractEvent{

	private String productId;
	
	private String bookingId;
	
	private int bookedQuantity;
	
	private float unitPrice;
}

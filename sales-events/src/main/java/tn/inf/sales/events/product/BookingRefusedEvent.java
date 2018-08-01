package tn.inf.sales.events.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@AllArgsConstructor
@ToString
public class BookingRefusedEvent extends AbstractEvent{

	private String productId;
	
	private int bookedQuantity;
	
	private String bookingId;
}

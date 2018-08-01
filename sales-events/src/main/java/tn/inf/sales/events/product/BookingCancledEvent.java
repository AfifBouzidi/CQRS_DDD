package tn.inf.sales.events.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@AllArgsConstructor
@ToString
public class BookingCancledEvent extends AbstractEvent{

	private String productId;
	
	private String bookingId;
}

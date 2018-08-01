package tn.inf.sales.commands.product;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.inf.sales.kernel.customer.BookingId;

@AllArgsConstructor
@Getter
public class CloseBookingCommand {

	@TargetAggregateIdentifier
	private String productId;
	
	private BookingId bookingId;
}

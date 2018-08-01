package tn.inf.sales.commands.product;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelBookingCommand {
	
	@TargetAggregateIdentifier
	private String productId;
	
	private String bookingId;

}

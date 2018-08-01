package tn.inf.sales.commands.product;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookProductCommand {

	@TargetAggregateIdentifier
	private String productId;
	
	private int quantity;
	
	private String bookingId;
	
}

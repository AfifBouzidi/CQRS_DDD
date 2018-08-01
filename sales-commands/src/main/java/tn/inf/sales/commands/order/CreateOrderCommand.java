package tn.inf.sales.commands.order;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.inf.sales.kernel.customer.CustomerId;

@Getter
@AllArgsConstructor
public class CreateOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
	
	private String customerId;
	
}

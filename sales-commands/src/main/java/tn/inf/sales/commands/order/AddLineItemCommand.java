package tn.inf.sales.commands.order;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.inf.sales.kernel.customer.ProductId;

@Getter
@AllArgsConstructor
public class AddLineItemCommand {

	@TargetAggregateIdentifier
	private String orderId;
	
	private String productId;
	
	private int quantity;
}

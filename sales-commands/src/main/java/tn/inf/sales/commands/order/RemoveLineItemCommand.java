package tn.inf.sales.commands.order;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tn.inf.sales.kernel.customer.LineItemId;

@Getter
@AllArgsConstructor
public class RemoveLineItemCommand {

	@TargetAggregateIdentifier
	private String orderId;

	private String itemId;

}

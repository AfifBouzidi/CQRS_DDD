package tn.inf.sales.commands.order;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CancelOrderCommand {

	@TargetAggregateIdentifier
	private String orderId;
}

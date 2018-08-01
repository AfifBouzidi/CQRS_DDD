package tn.inf.sales.commands.customer;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ActivateCustomerAccountCommand {

	@TargetAggregateIdentifier
	private String customerId;
}

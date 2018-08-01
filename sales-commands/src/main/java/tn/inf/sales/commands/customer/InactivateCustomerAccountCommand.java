package tn.inf.sales.commands.customer;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InactivateCustomerAccountCommand {

	@TargetAggregateIdentifier
	private String customerId;
}

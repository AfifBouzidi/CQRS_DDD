package tn.inf.sales.commands.customer;

import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RelocateCustomerCommand {

	@TargetAggregateIdentifier
	private String customerId;

	@NotNull
	private String street;
	
	@NotNull
	private String city;
	
	@NotNull
	private String country;
}

package tn.inf.sales.commands.customer;

import javax.validation.constraints.NotNull;

import org.axonframework.commandhandling.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateCustomerAccountCommand {

	@TargetAggregateIdentifier
	private String customerId;

	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String street;
	
	@NotNull
	private String city;
	
	@NotNull
	private String country;

}

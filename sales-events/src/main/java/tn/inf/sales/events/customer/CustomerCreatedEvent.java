package tn.inf.sales.events.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@Builder
@ToString
public class CustomerCreatedEvent extends AbstractEvent {

	private String customerId;
	
	private String firstName;
	
	private String lastName;
	
	private String street;
	
	private String city;
	
	private String country;
}

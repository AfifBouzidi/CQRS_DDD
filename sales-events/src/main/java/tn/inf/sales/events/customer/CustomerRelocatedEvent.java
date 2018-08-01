package tn.inf.sales.events.customer;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@Builder
@ToString
public class CustomerRelocatedEvent extends AbstractEvent {

	private String customerId;

	private String street;

	private String city;

	private String country;
}

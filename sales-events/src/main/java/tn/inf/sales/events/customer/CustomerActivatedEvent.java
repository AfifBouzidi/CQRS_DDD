package tn.inf.sales.events.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@AllArgsConstructor
@ToString
public class CustomerActivatedEvent extends AbstractEvent {

	private String customerId;
}

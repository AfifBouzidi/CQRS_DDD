package tn.inf.sales.domain.customer;

import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.inf.sales.commands.customer.ActivateCustomerAccountCommand;
import tn.inf.sales.commands.customer.CreateCustomerAccountCommand;
import tn.inf.sales.commands.customer.InactivateCustomerAccountCommand;
import tn.inf.sales.commands.customer.RelocateCustomerCommand;
import tn.inf.sales.events.customer.CustomerActivatedEvent;
import tn.inf.sales.events.customer.CustomerCreatedEvent;
import tn.inf.sales.events.customer.CustomerInactivatedEvent;
import tn.inf.sales.events.customer.CustomerRelocatedEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.CustomerStatus;

@Getter
@NoArgsConstructor
@Entity
@Aggregate(repository = "customerAggregateRepository")
public class CustomerAggregate {

	@EmbeddedId
	@AggregateIdentifier
	private CustomerId customerId;

	@Enumerated(EnumType.STRING)
	private CustomerStatus customerStatus;

	private String firstName;

	private String lastName;

	@Embedded
	private Address address;

	@CommandHandler
	public CustomerAggregate(CreateCustomerAccountCommand createCustomerAccountCommand) {
		String id = Objects.isNull(createCustomerAccountCommand.getCustomerId())
				? IdentifierFactory.getInstance().generateIdentifier()
				: createCustomerAccountCommand.getCustomerId();
		this.customerId = new CustomerId(id);
		this.address = new Address(createCustomerAccountCommand.getStreet(), createCustomerAccountCommand.getCity(),
				createCustomerAccountCommand.getCountry());
		this.firstName = createCustomerAccountCommand.getFirstName();
		this.lastName = createCustomerAccountCommand.getLastName();
		this.customerStatus = CustomerStatus.ACTIVE;
		CustomerCreatedEvent customerCreatedEvent = CustomerCreatedEvent.builder().city(this.address.getCity())
				.country(this.address.getCountry()).street(this.address.getStreet()).firstName(this.firstName)
				.lastName(this.lastName).customerId(this.customerId.getCustomerId()).build();
		AggregateLifecycle.apply(customerCreatedEvent);
	}

	@CommandHandler
	public void handle(ActivateCustomerAccountCommand activateCustomerAccountCommand) {
		this.customerStatus = CustomerStatus.ACTIVE;
		AggregateLifecycle.apply(new CustomerActivatedEvent(this.customerId.getCustomerId()));
	}

	@CommandHandler
	public void handle(InactivateCustomerAccountCommand inActivateCustomerAccountCommand) {
		this.customerStatus = CustomerStatus.INACTIVE;
		AggregateLifecycle.apply(new CustomerInactivatedEvent(this.customerId.getCustomerId()));
	}

	@CommandHandler
	public void handle(RelocateCustomerCommand relocateCustomerCommand) {
		this.address = new Address(relocateCustomerCommand.getStreet(), relocateCustomerCommand.getCity(),
				relocateCustomerCommand.getCountry());
		AggregateLifecycle.apply(CustomerRelocatedEvent.builder().city(this.address.getCity())
				.country(this.address.getCountry()).street(this.address.getStreet()));
	}

}

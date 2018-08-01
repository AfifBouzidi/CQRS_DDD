package tn.inf.sales.domain.invoice;

import java.util.Map;
import java.util.Objects;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.inf.sales.commands.invoice.CancelInvoiceCommand;
import tn.inf.sales.commands.invoice.CloseInvoiceCommand;
import tn.inf.sales.commands.invoice.CreateInvoiceCommand;
import tn.inf.sales.events.invoice.InvoiceCancledEvent;
import tn.inf.sales.events.invoice.InvoiceClosedEvent;
import tn.inf.sales.events.invoice.InvoiceCreatedEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.InvoiceId;
import tn.inf.sales.kernel.customer.InvoiceStatus;
import tn.inf.sales.kernel.customer.OrderId;

@Getter
@NoArgsConstructor
@Entity
@Aggregate(repository = "invoiceAggregateRepository")
public class InvoiceAggregate {

	@EmbeddedId
	@AggregateIdentifier
	private InvoiceId invoiceId;

	@Enumerated(EnumType.STRING)
	private InvoiceStatus invoiceStatus;

	@Embedded
	private CustomerId customerId;

	@Embedded
	private OrderId orderId;

	private float totalAmount;

	@CommandHandler
	public InvoiceAggregate(CreateInvoiceCommand createInvoiceCommand) {
		String id = Objects.isNull(createInvoiceCommand.getInvoiceId())
				? IdentifierFactory.getInstance().generateIdentifier()
				: createInvoiceCommand.getInvoiceId();
		this.invoiceId = new InvoiceId(id);
		this.customerId = new CustomerId(createInvoiceCommand.getCustomerId());
		this.orderId = new OrderId(createInvoiceCommand.getOrderId());
		this.invoiceStatus = InvoiceStatus.CREATED;
		this.totalAmount=calculateTotalAmount(createInvoiceCommand);
		AggregateLifecycle.apply(new InvoiceCreatedEvent(invoiceId.getInvoiceId(),customerId.getCustomerId(),orderId.getOrderId(),totalAmount));
	}

	@CommandHandler
	public void handle(CancelInvoiceCommand cancelInvoiceCommand) {
		this.invoiceStatus = InvoiceStatus.CANCLED;
		AggregateLifecycle.apply(new InvoiceCancledEvent(invoiceId.getInvoiceId()));
	}
	
	@CommandHandler
	public void handle(CloseInvoiceCommand cancelInvoiceCommand) {
		this.invoiceStatus = InvoiceStatus.PAID;
		AggregateLifecycle.apply(new InvoiceClosedEvent(invoiceId.getInvoiceId()));
	}
	
	private float calculateTotalAmount(CreateInvoiceCommand createInvoiceCommand)
	{
		Map<String,Float> priceMap=createInvoiceCommand.getProductPriceMap();
		Map<String,Integer> quatityMap=createInvoiceCommand.getProductQuantityMap();
		priceMap.replaceAll((key, value) -> value*quatityMap.get(key));
		Float total = (float) priceMap.values().stream().mapToDouble(Float::floatValue).sum();
		return total;
	}

}

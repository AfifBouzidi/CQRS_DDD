package tn.inf.sales.commands.invoice;

import java.util.Map;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.Builder;
import lombok.Getter;
import tn.inf.sales.kernel.customer.CustomerId;

@Getter
@Builder
public class CreateInvoiceCommand {

	@TargetAggregateIdentifier
	private String invoiceId;
	
	private String orderId;
	
	private String customerId;
	
	private Map<String,Integer> productQuantityMap;
	
	private Map<String,Float> productPriceMap;
	
}

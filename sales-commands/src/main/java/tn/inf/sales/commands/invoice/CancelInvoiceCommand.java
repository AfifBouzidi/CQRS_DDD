package tn.inf.sales.commands.invoice;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelInvoiceCommand {

	@TargetAggregateIdentifier
	private String invoiceId;
}

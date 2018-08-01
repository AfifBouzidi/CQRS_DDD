package tn.inf.sales.commands.invoice;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CloseInvoiceCommand {

	@TargetAggregateIdentifier
	private String invoiceId;
}

package tn.inf.sales.events.invoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;


@Getter
@AllArgsConstructor
@ToString
public class InvoiceClosedEvent  extends AbstractEvent {

	private String invoiceId;
}

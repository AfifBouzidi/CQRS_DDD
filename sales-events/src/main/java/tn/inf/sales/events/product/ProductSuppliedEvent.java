package tn.inf.sales.events.product;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import tn.inf.sales.events.AbstractEvent;

@Getter
@Builder
@ToString
public class ProductSuppliedEvent extends AbstractEvent {

	private String productId;

	private String reference;

	private int newQuantity;
	
	private int addedQuantity;
}

package tn.inf.sales.commands.product;

import org.axonframework.commandhandling.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateProductCommand {

	@TargetAggregateIdentifier
	private String productId;

	private String reference;

	private int quantity;
	
	private float unitPrice;
}

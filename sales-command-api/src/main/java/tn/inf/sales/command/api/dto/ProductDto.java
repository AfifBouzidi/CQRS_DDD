package tn.inf.sales.command.api.dto;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductDto {

	private String reference;

	private int quantity;
	
	private float unitPrice;
}

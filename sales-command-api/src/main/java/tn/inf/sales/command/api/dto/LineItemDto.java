package tn.inf.sales.command.api.dto;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class LineItemDto {
	
	@NotEmpty
	private String productId;

	@Min(value=0)
	private Integer quantity;

}

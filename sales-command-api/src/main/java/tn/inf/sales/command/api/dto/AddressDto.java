package tn.inf.sales.command.api.dto;


import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;

@Getter
public class AddressDto {
	
	@NotEmpty
	private String street;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String country;
}

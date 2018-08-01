package tn.inf.sales.command.api.dto;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomerDto {
	
	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String street;

	@NotEmpty
	private String city;

	@NotEmpty
	private String country;
}

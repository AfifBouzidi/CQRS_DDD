package tn.inf.sales.domain.customer;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
class Address {
	private String street;
	private String city;
	private String country;
}

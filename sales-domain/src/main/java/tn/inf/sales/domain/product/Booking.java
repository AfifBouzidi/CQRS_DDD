package tn.inf.sales.domain.product;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.axonframework.commandhandling.model.EntityId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.inf.sales.kernel.customer.BookingId;


@Getter
@Setter(value = AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
class Booking {
	
	@EmbeddedId
	@EntityId(routingKey = "bookingId")
	private BookingId bookingId;
	
	private int bookedQuantity;

}

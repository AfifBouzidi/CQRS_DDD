package tn.inf.sales.domain.order;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.axonframework.commandhandling.model.EntityId;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.inf.sales.kernel.customer.LineItemId;
import tn.inf.sales.kernel.customer.ProductId;


@Getter
@Setter(value = AccessLevel.PACKAGE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
class LineItem {

	@EmbeddedId
	@EntityId(routingKey = "itemId")
	private LineItemId itemId;

	@Embedded
	private ProductId productId;

	private int quantity;

}

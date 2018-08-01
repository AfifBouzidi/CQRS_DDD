package tn.inf.sales.domain.product;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.spring.stereotype.Aggregate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tn.inf.sales.commands.product.BookProductCommand;
import tn.inf.sales.commands.product.CancelBookingCommand;
import tn.inf.sales.commands.product.CloseBookingCommand;
import tn.inf.sales.commands.product.CreateProductCommand;
import tn.inf.sales.commands.product.SupplyCommand;
import tn.inf.sales.events.product.BookingCancledEvent;
import tn.inf.sales.events.product.BookingClosedEvent;
import tn.inf.sales.events.product.BookingRefusedEvent;
import tn.inf.sales.events.product.ProductBookedEvent;
import tn.inf.sales.events.product.ProductCreatedEvent;
import tn.inf.sales.kernel.customer.BookingId;
import tn.inf.sales.kernel.customer.ProductId;

@Getter
@NoArgsConstructor
@Entity
@Aggregate(repository = "productAggregateRepository")
public class ProductAggregate {

	@EmbeddedId
	@AggregateIdentifier
	private ProductId productId;

	private String reference;

	private int availableQuantity;

	private int soldQuantity;

	private float unitPrice;

	@AggregateMember
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Booking> bookings;

	@CommandHandler
	public ProductAggregate(CreateProductCommand createProductCommand) {
		String id = Objects.isNull(createProductCommand.getProductId())
				? IdentifierFactory.getInstance().generateIdentifier()
				: createProductCommand.getProductId();
		this.productId = new ProductId(id);
		this.reference = createProductCommand.getReference();
		this.availableQuantity = createProductCommand.getQuantity();
		this.unitPrice = createProductCommand.getUnitPrice();
		AggregateLifecycle.apply(ProductCreatedEvent.builder().productId(this.productId.getProductId())
				.reference(this.reference).quantity(this.availableQuantity).unitPrice(this.unitPrice).build());
	}

	@CommandHandler
	public void handle(SupplyCommand supplyCommand) {
		this.availableQuantity += supplyCommand.getQuantity();
	}

	@CommandHandler
	public void handle(BookProductCommand bookProductCommand) {
		if (bookProductCommand.getQuantity() > this.availableQuantity) {
			AggregateLifecycle.apply(new BookingRefusedEvent(this.productId.getProductId(), this.availableQuantity,
					bookProductCommand.getBookingId()));
		} else {
			String id = Objects.isNull(bookProductCommand.getBookingId())
					? IdentifierFactory.getInstance().generateIdentifier()
					: bookProductCommand.getBookingId();

			BookingId bookingId = new BookingId(id);
			Booking booking = new Booking(bookingId, bookProductCommand.getQuantity());
			bookings.add(booking);
			AggregateLifecycle.apply(ProductBookedEvent.builder().productId(this.productId.getProductId())
					.bookedQuantity(bookProductCommand.getQuantity()).bookingId(bookingId.getBookingId())
					.unitPrice(this.unitPrice).build());
		}
	}

	@CommandHandler
	public void handle(CancelBookingCommand cancelBookingCommand) {
		Optional<Booking> optional = bookings.stream()
				.filter((Booking booking) -> booking.getBookingId().equals(cancelBookingCommand.getBookingId()))
				.findFirst();
		if (optional.isPresent()) {
			Booking booking = optional.get();
			bookings.remove(booking);
			this.availableQuantity += booking.getBookedQuantity();
			AggregateLifecycle.apply(
					new BookingCancledEvent(this.productId.getProductId(), booking.getBookingId().getBookingId()));
		}
	}

	@CommandHandler
	public void handle(CloseBookingCommand closeBookingCommand) {
		bookings.removeIf((Booking booking) -> booking.getBookingId().equals(closeBookingCommand.getBookingId()));
		AggregateLifecycle.apply(new BookingClosedEvent(this.productId.getProductId(),
				closeBookingCommand.getBookingId().getBookingId()));
	}
}

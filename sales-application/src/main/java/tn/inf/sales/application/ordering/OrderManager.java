package tn.inf.sales.application.ordering;

import java.util.HashMap;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.common.IdentifierFactory;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.SagaLifecycle;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tn.inf.sales.commands.invoice.CreateInvoiceCommand;
import tn.inf.sales.commands.order.CancelOrderCommand;
import tn.inf.sales.commands.product.BookProductCommand;
import tn.inf.sales.commands.product.CancelBookingCommand;
import tn.inf.sales.domain.order.OrderAggregate;
import tn.inf.sales.events.order.OrderPlacedEvent;
import tn.inf.sales.events.product.BookingRefusedEvent;
import tn.inf.sales.events.product.ProductBookedEvent;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.OrderId;

@Slf4j
@Component
@Saga
public class OrderManager {

	private OrderId orderId;

	private CustomerId customerId;

	private Map<String, Integer> productQuantityMap;

	private Map<String, Float> productPriceMap = new HashMap<>();

	private Map<String, String> confirmedBooking = new HashMap<>();

	private int refusedBookingCount;

	private int bookingCount;

	@Autowired
	private transient CommandGateway commandGateway;

	@Autowired
	private transient GenericJpaRepository<OrderAggregate> orderAggregateRepository;

	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handle(OrderPlacedEvent orderPlacedEvent) {
		log.info("OrderManager start saga : "+orderPlacedEvent.toString());
		orderId=new OrderId(orderPlacedEvent.getOrderId());
		customerId=new CustomerId(orderPlacedEvent.getCustomerId());
		productQuantityMap = orderPlacedEvent.getProductQuantityMap();
		bookingCount = productQuantityMap.size();
		productQuantityMap.forEach((productId, quantity) -> {
			String bookingId = IdentifierFactory.getInstance().generateIdentifier();
			SagaLifecycle.associateWith("bookingId", bookingId);
			commandGateway.send(new BookProductCommand(productId, quantity, bookingId));
		});
	}

	@SagaEventHandler(associationProperty = "bookingId")
	public void handle(ProductBookedEvent productBookedEvent) {
		log.info("OrderManager saga : "+productBookedEvent.toString());
		log.info("OrderManager saga ProductId: "+productBookedEvent.getProductId());
		log.info("OrderManager saga BookingId: "+productBookedEvent.getBookingId());
		log.info("OrderManager saga confirmedBooking: "+confirmedBooking);
		log.info("OrderManager saga productPriceMap: "+productPriceMap);
		confirmedBooking.put(productBookedEvent.getProductId(), productBookedEvent.getBookingId());
		productPriceMap.put(productBookedEvent.getProductId(), productBookedEvent.getUnitPrice());
		performOrdering();
	}

	@SagaEventHandler(associationProperty = "bookingId")
	public void handle(BookingRefusedEvent bookingRefusedEvent) {
		log.info("OrderManager saga : "+bookingRefusedEvent.toString());
		refusedBookingCount += 1;
		performOrdering();
	}

	private void performOrdering() {
		if (isSageEnd() && isOrderShouldBeCancled()) {
			cancelBookings();
			cancelOrder();
			SagaLifecycle.end();
		} else if (isSageEnd() && !isOrderShouldBeCancled()) {
			createInvoice();
			SagaLifecycle.end();
		}
	}

	private void createInvoice() {
		commandGateway.send(
				CreateInvoiceCommand.builder().customerId(customerId.getCustomerId()).orderId(orderId.getOrderId())
						.productPriceMap(productPriceMap).productQuantityMap(productQuantityMap).build());
	}

	private void cancelBookings() {
		confirmedBooking.forEach((String productId, String bookingId) -> commandGateway
				.send(new CancelBookingCommand(productId, bookingId)));

	}

	private void cancelOrder() {
		commandGateway.send(new CancelOrderCommand(orderId.getOrderId()));
	}

	private boolean isSageEnd() {
		return bookingCount == (refusedBookingCount + confirmedBooking.size());
	}

	private boolean isOrderShouldBeCancled() {
		return refusedBookingCount != 0;
	}

}

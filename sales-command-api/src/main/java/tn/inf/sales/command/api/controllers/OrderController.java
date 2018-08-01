package tn.inf.sales.command.api.controllers;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wordnik.swagger.annotations.Api;

import lombok.extern.slf4j.Slf4j;
import tn.inf.sales.command.api.dto.LineItemDto;
import tn.inf.sales.commands.order.AddLineItemCommand;
import tn.inf.sales.commands.order.CancelOrderCommand;
import tn.inf.sales.commands.order.CloseOrderCommand;
import tn.inf.sales.commands.order.CreateOrderCommand;
import tn.inf.sales.commands.order.PlaceOrderCommand;
import tn.inf.sales.commands.order.RemoveLineItemCommand;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.LineItemId;
import tn.inf.sales.kernel.customer.OrderId;
import tn.inf.sales.kernel.customer.ProductId;

@Slf4j
@RestController
@Api(value = "orders", description = "Order API")
public class OrderController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(value = "/customers/{customerId}/orders", method = RequestMethod.POST)
	public ResponseEntity<?> createOrder(@PathVariable String customerId) {
		CreateOrderCommand createOrderCommand = new CreateOrderCommand(null,customerId);
		return new ResponseEntity<>(null, createOrder(createOrderCommand), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/customers/{customerId}/orders/{orderId}/status/{status}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeOrderStatus(@PathVariable String status, @PathVariable String orderId) {
		if ("CANCEL".equals(status)) {
			commandGateway.send(new CancelOrderCommand(orderId));
		} else if ("CLOSE".equals(status)) {
			commandGateway.send(new CloseOrderCommand(orderId));
		} else if ("PROCESS".equals(status)) {
			commandGateway.send(new PlaceOrderCommand(orderId));
		} else {
			throw new IllegalArgumentException("status : " + status);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/customers/{customerId}/orders/{orderId}/lineItems/{lineItemId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeLineItem(@PathVariable String orderId, @PathVariable String lineItemId) {
		commandGateway.send(new RemoveLineItemCommand(orderId,lineItemId));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/customers/{customerId}/orders/{orderId}/lineItems", method = RequestMethod.POST)
	public ResponseEntity<?> addLineItem(@Valid @RequestBody LineItemDto lineItemDto, @PathVariable String orderId,
			@PathVariable String customerId) {
		commandGateway.send(new AddLineItemCommand(orderId, lineItemDto.getProductId(), lineItemDto.getQuantity()));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private HttpHeaders createOrder(CreateOrderCommand createOrderCommand) {
		HttpHeaders responseHeaders = null;
		try {
			OrderId id = (OrderId) commandGateway.send(createOrderCommand).get();
			responseHeaders = new HttpHeaders();
			URI newCustomerUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(id.getOrderId()).toUri();
			responseHeaders.setLocation(newCustomerUri);
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error while creating order ", e);
		}
		return responseHeaders;
	}

}

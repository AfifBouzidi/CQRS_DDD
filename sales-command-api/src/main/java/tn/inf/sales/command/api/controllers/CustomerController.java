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
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponses;
import com.wordnik.swagger.annotations.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import tn.inf.sales.command.api.dto.AddressDto;
import tn.inf.sales.command.api.dto.CustomerDto;
import tn.inf.sales.command.api.exception.ErrorDetail;
import tn.inf.sales.commands.customer.ActivateCustomerAccountCommand;
import tn.inf.sales.commands.customer.CreateCustomerAccountCommand;
import tn.inf.sales.commands.customer.InactivateCustomerAccountCommand;
import tn.inf.sales.commands.customer.RelocateCustomerCommand;
import tn.inf.sales.kernel.customer.CustomerId;

@Slf4j
@RestController
@Api(value = "customers", description = "Customer API")
public class CustomerController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	@ApiOperation(value = "Creates a new customer account", notes = "The newly created account Id will be sent in the location response header", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "account Created Successfully", response = Void.class),
			@ApiResponse(code = 500, message = "Error creating account", response = ErrorDetail.class) })
	public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
		log.info("Create customer :" + customerDto);
		CreateCustomerAccountCommand createCustomerAccountCommand = CreateCustomerAccountCommand.builder()
				.city(customerDto.getCity()).country(customerDto.getCountry())
				.firstName(customerDto.getFirstName()).lastName(customerDto.getLastName())
				.street(customerDto.getStreet()).build();
		return new ResponseEntity<>(null, createCustomer(createCustomerAccountCommand), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/customers/{customerId}/status/{status}", method = RequestMethod.PUT)
	@ApiOperation(value = "change status", notes = "change customer status", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "status changed Successfully", response = Void.class),
			@ApiResponse(code = 500, message = "Error changing status", response = ErrorDetail.class) })
	public ResponseEntity<?> changeCustomerStatus(@PathVariable String status, @PathVariable String customerId) {
		log.info("status " + status);
		if ("ACTIVE".equals(status)) {
			commandGateway.send(new ActivateCustomerAccountCommand(customerId));
		} else if ("INACTIVE".equals(status)) {
			commandGateway.send(new InactivateCustomerAccountCommand(customerId));
		} else {
			throw new IllegalArgumentException("status : " + status);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/customers/{customerId}/address", method = RequestMethod.PUT)
	@ApiOperation(value = "relocate customer", notes = "change customer address", response = Void.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "address changed Successfully", response = Void.class),
			@ApiResponse(code = 500, message = "Error changing address", response = ErrorDetail.class) })
	public ResponseEntity<?> relocateCustomer(@PathVariable String customerId,
			@Valid @RequestBody AddressDto addressDto) {
		RelocateCustomerCommand relocateCustomerCommand = RelocateCustomerCommand.builder().customerId(customerId)
				.city(addressDto.getCity()).country(addressDto.getCountry()).street(addressDto.getStreet()).build();
		commandGateway.send(relocateCustomerCommand);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private HttpHeaders createCustomer(CreateCustomerAccountCommand createCustomerAccountCommand) {
		HttpHeaders responseHeaders = null;
		try {
			CustomerId id = (CustomerId) commandGateway.send(createCustomerAccountCommand).get();
			responseHeaders = new HttpHeaders();
			URI newCustomerUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(id.getCustomerId()).toUri();
			responseHeaders.setLocation(newCustomerUri);
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error while creating customer account ", e);
		}
		return responseHeaders;

	}

}

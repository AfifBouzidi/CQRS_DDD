package tn.inf.sales.command.api.controllers;

import java.net.URI;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.axonframework.commandhandling.gateway.CommandGateway;
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
import tn.inf.sales.command.api.dto.ProductDto;
import tn.inf.sales.commands.product.CreateProductCommand;
import tn.inf.sales.commands.product.SupplyCommand;
import tn.inf.sales.kernel.customer.ProductId;

@Slf4j
@RestController
@Api(value = "products", description = "Product API")
public class ProductController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
		CreateProductCommand createProductCommand = CreateProductCommand.builder().quantity(productDto.getQuantity())
				.unitPrice(productDto.getUnitPrice()).reference(productDto.getReference()).build();
		return new ResponseEntity<>(null, createProduct(createProductCommand), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products/{productId}/quantity/{quantity}", method = RequestMethod.PUT)
	public ResponseEntity<?> supplyProduct(@PathVariable int quantity, @PathVariable String productId) {
		SupplyCommand supplyCommand = new SupplyCommand(productId, quantity);
		commandGateway.send(supplyCommand);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	private HttpHeaders createProduct(CreateProductCommand createProductCommand) {
		HttpHeaders responseHeaders = null;
		try {
			ProductId id = (ProductId) commandGateway.send(createProductCommand).get();
			responseHeaders = new HttpHeaders();
			URI newCustomerUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(id.getProductId()).toUri();
			responseHeaders.setLocation(newCustomerUri);
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error while creating product account ", e);
		}
		return responseHeaders;
	}
}

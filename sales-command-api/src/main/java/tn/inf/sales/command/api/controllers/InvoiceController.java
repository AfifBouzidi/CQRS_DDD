package tn.inf.sales.command.api.controllers;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

import lombok.extern.slf4j.Slf4j;
import tn.inf.sales.commands.invoice.CancelInvoiceCommand;
import tn.inf.sales.commands.invoice.CloseInvoiceCommand;

@Slf4j
@RestController
@Api(value = "invoices", description = "Invoice API")
public class InvoiceController {

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(value = "/invoices/{invoiceId}/status/{status}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeInvoiceStatus(@PathVariable String status, @PathVariable String invoiceId) {
		log.info("invoice status " + status);
		if ("CLOSE".equals(status)) {
			commandGateway.send(new CloseInvoiceCommand(invoiceId));
		} else if ("CANCEL".equals(status)) {
			commandGateway.send(new CancelInvoiceCommand(invoiceId));
		} else {
			throw new IllegalArgumentException("status : " + status);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

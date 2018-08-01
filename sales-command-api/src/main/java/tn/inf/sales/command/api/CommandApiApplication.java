package tn.inf.sales.command.api;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

import tn.inf.sales.commands.customer.CreateCustomerAccountCommand;
import tn.inf.sales.commands.customer.InactivateCustomerAccountCommand;
import tn.inf.sales.infra.InfraApplication;
import tn.inf.sales.kernel.customer.CustomerId;

@SpringBootApplication
@PropertySource({ "classpath:command-api.properties" })
public class CommandApiApplication implements CommandLineRunner {

	@Autowired
	private CommandGateway commandGateway;

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE)
				.sources(CommandApiApplication.class, InfraApplication.class).run(args);

	}

	@Override
	public void run(String... arg0) throws Exception {
//		CustomerId c=	new CustomerId(IdentifierFactory.getInstance().generateIdentifier());
//		CreateCustomerAccountCommand cmd = CreateCustomerAccountCommand.builder().city("Tunis").country("Tunisia")
//				.firstName("Afif").lastName("Bouzidi").street("street one").customerId(c).build();
//		commandGateway.send(cmd);
//		commandGateway.send(new InactivateCustomerAccountCommand(c));
	}

}

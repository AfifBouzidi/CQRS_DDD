package tn.inf.sales.infra.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.model.GenericJpaRepository;
import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.interceptors.EventLoggingInterceptor;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import tn.inf.sales.domain.customer.CustomerAggregate;
import tn.inf.sales.domain.invoice.InvoiceAggregate;
import tn.inf.sales.domain.order.OrderAggregate;
import tn.inf.sales.domain.product.ProductAggregate;
import tn.inf.sales.kernel.customer.CustomerId;
import tn.inf.sales.kernel.customer.InvoiceId;
import tn.inf.sales.kernel.customer.OrderId;
import tn.inf.sales.kernel.customer.ProductId;

@Slf4j
@Configuration
public class AxonConfig {

	@Autowired
	EntityManagerProvider entityManagerProvider;

	@Autowired
	EventBus eventBus;

	@Bean(name = "eventBus")
	public SimpleEventBus eventBus(AxonConfiguration configuration) {
		SimpleEventBus eventBus = new SimpleEventBus(Integer.MAX_VALUE,
				configuration.messageMonitor(EventStore.class, "eventStore"));
		eventBus.registerDispatchInterceptor(new EventLoggingInterceptor());
		return eventBus;
	}

	@Autowired
	public void configureValidation(CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(new BeanValidationInterceptor());
	}

	@Bean
	public GenericJpaRepository<CustomerAggregate> customerAggregateRepository() {
		return new GenericJpaRepository<>(entityManagerProvider, CustomerAggregate.class, eventBus, (String id) -> new CustomerId(id));
	}
	
	@Bean
	public GenericJpaRepository<OrderAggregate> orderAggregateRepository() {
		return new GenericJpaRepository<>(entityManagerProvider, OrderAggregate.class, eventBus, (String id) -> new OrderId(id));
	}
	
	@Bean
	public GenericJpaRepository<ProductAggregate> productAggregateRepository() {
		return new GenericJpaRepository<>(entityManagerProvider, ProductAggregate.class, eventBus, (String id) -> new ProductId(id));
	}
	
	@Bean
	public GenericJpaRepository<InvoiceAggregate> invoiceAggregateRepository() {
		return new GenericJpaRepository<>(entityManagerProvider, InvoiceAggregate.class, eventBus, (String id) -> new InvoiceId(id));
	}


}

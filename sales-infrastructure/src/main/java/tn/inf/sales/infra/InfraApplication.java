package tn.inf.sales.infra;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import tn.inf.sales.infra.config.AxonConfig;

@SpringBootApplication
@Import({AxonConfig.class})
@PropertySource({"classpath:infrastructure.properties"})
@ComponentScan(basePackages = {"tn.inf.sales.domain","tn.inf.sales.application"})
@EntityScan(basePackages = { "tn.inf.sales.domain","org.axonframework.eventsourcing.eventstore.jpa","org.axonframework.eventhandling.saga.repository.jpa","org.axonframework.eventhandling.tokenstore.jpa"})
public class InfraApplication {

}

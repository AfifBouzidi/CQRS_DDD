package tn.inf.sales.query.api;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource({"classpath:query-api.properties"})
public class QueryApiApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder().bannerMode(Banner.Mode.CONSOLE).sources(QueryApiApplication.class).run(args);
	}

}

package tn.inf.sales.command.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.models.dto.builder.ApiInfoBuilder;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@Configuration
@EnableSwagger
public class SwaggerConfig {

	@Autowired
	private SpringSwaggerConfig springSwaggerConfig;

	@Bean
	public SwaggerSpringMvcPlugin configureSwagger() {
		SwaggerSpringMvcPlugin swaggerSpringMvcPlugin = new SwaggerSpringMvcPlugin(this.springSwaggerConfig);
		ApiInfo apiInfo = new ApiInfoBuilder().title("Sales REST API")
				.description("A small sales Rest Api")
				.termsOfServiceUrl("http://abouzidi.com/terms-of-service").contact("afif.bouzidi@gmail.com")
				.license("MIT License").licenseUrl("http://opensource.org/licenses/MIT").build();
		swaggerSpringMvcPlugin.apiInfo(apiInfo).apiVersion("1.0").includePatterns("/customers/*.*","/products/*.*","/invoices/*.*");
		swaggerSpringMvcPlugin.useDefaultResponseMessages(false);
		return swaggerSpringMvcPlugin;
	}

}

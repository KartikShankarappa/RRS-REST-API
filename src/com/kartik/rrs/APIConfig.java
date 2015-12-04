package com.kartik.rrs;

import io.swagger.jaxrs.config.BeanConfig;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("/api")
public class APIConfig extends ResourceConfig {

	public APIConfig() {
		// TODO Auto-generated constructor stub
		packages("com.kartik.rest");
		
        register(io.swagger.jaxrs.listing.ApiListingResource.class);
        register(io.swagger.jaxrs.listing.SwaggerSerializers.class);
		
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/RRSRestApp/api");
        beanConfig.setResourcePackage("com.kartik");
        beanConfig.setDescription("REST API for Restaurant Reservation System");
        beanConfig.setScan(true);
        beanConfig.setTitle("com.kartik");
	}
}

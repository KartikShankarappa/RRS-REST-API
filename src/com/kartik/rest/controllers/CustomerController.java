package com.kartik.rest.controllers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.kartik.dao.AdminDAO;
import com.kartik.dao.CustomerDAO;
import com.kartik.entities.Customer;
import com.kartik.exceptions.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/customer_database")
@Api(tags = {"Customers"})
public class CustomerController {
	
	@GET
	@Path("/{emailId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Find All Reservations",
					notes = "Find all the reservations in the system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public List<Customer> findAllCustomers(@PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			CustomerDAO customerDAO = new CustomerDAO();
			try {
				return customerDAO.listAllCustomers();
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}
}

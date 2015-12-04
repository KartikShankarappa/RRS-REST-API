package com.kartik.rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.kartik.dao.AdminDAO;
import com.kartik.dao.RestaurantDAO;
import com.kartik.entities.Restaurant;
import com.kartik.exceptions.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/profile")
@Api(tags = {"Profile"})
public class ResturantController {
	
	@GET
	@Path("/{emailId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Restaurant Profile",
					notes = "View restaurant's profile"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Restaurant restaurantDetails(@PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			RestaurantDAO restaurantDAO = new RestaurantDAO();
			
			try {
				return restaurantDAO.findRestaurantDetails();
			} catch (AppException e) {
				e.printStackTrace();
				throw new WebApplicationException(Status.UNAUTHORIZED);
			} 
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
	}
	
	
	@PUT
	@Path("/{emailId}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Update Profile",
					notes = "Update the restaurant's profile"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Restaurant editRestaurantDetails(@PathParam("emailId") String emailId, @PathParam("token") String token, Restaurant restaurant) {
		
		if(AdminDAO.isSessionValid(emailId, token)){
			RestaurantDAO restaurantDAO = new RestaurantDAO();
			
			try {
				return restaurantDAO.updateRestaurantDetails(restaurant);
			} catch (AppException e) {
				e.printStackTrace();
				throw new WebApplicationException(Status.UNAUTHORIZED);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
	}

}

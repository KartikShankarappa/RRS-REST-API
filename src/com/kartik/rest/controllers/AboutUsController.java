package com.kartik.rest.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.kartik.dao.RestaurantDAO;
import com.kartik.entities.Restaurant;
import com.kartik.exceptions.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/about_us")
@Api(tags = {"About Us"})
public class AboutUsController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Restaurant Details",
					notes = "Display restuarant details like address, contact information, business hours, etc"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Restaurant restaurantDetails() {
		
		Restaurant restaurant = new Restaurant();
		RestaurantDAO restaurantDAO = new RestaurantDAO();
		
		try {
			restaurant = restaurantDAO.findRestaurantDetails();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		} 
		return restaurant;
	}

}

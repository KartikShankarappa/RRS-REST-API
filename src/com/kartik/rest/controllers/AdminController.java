package com.kartik.rest.controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.kartik.dao.AdminDAO;
import com.kartik.entities.Admin;
import com.kartik.exceptions.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/authentication")
@Api(tags = {"Authentication"})
public class AdminController {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Login",
					notes = "Validate the login credentials by taking the username/email id as inputs"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Admin login(Admin loginDetails) {
		
		Admin admin = new Admin();
		AdminDAO adminDAO = new AdminDAO();
		
		try {
			admin = adminDAO.authenticateUser(loginDetails);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		if(admin.getToken() == null) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		return admin;
		
	}
		
	@DELETE
	@Path("/logout")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Logout",
					notes = "Logout off the reservation system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Admin logout(Admin loginDetails)
	{	
		Admin admin = null;
		AdminDAO adminDAO = new AdminDAO();
		admin = adminDAO.logout(loginDetails);
		return admin;
	}
}

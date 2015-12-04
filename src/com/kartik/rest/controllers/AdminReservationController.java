package com.kartik.rest.controllers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.kartik.dao.AdminDAO;
import com.kartik.dao.ReservationDAO;
import com.kartik.dao.TableDAO;
import com.kartik.entities.Reservation;
import com.kartik.exceptions.AppException;


@Path("admin/reservations")
@Api(tags = {"Admin Reservations"})
public class AdminReservationController {
	
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
	public List<Reservation> findAllReservations(@PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			ReservationDAO reservationDAO = new ReservationDAO();
			
			try {
				return reservationDAO.listAllReservations();
			} catch (AppException e) {
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		
		
	}
	
	
	@GET
	@Path("/{id}/{emailId}/{token}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Find One Reservation",
					notes = "Find a reservation by specifying the confirmation number in the system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})	
	public Reservation findOneReservation(@PathParam("emailId") String emailId, @PathParam("token") String token, @PathParam ("id") int confNo) {
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			ReservationDAO reservationDAO = new ReservationDAO();
			
			try {
				return reservationDAO.listOneReservation(confNo); 
			} catch (AppException e) {
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}	

	}
	
	
	@POST
	@Path("/makeReservation/{emailId}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Make Reservation",
					notes = "Make a new Reservation"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Reservation createReservation(Reservation reservation, @PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		
		if (AdminDAO.isSessionValid(emailId, token)) {
			
			ReservationDAO reservationDAO = new ReservationDAO();
			TableDAO tableDAO = new TableDAO();
			int tableNo;
			
			try {
				tableNo = tableDAO.findTable(reservation);
				if(tableNo != -1) {
					reservation.setTableNumber(tableNo);
					reservation.setReservationStatus("CONFIRMED");
				} else {
					reservation.setReservationStatus("WAITING");
				}
				
				return reservationDAO.createNewReservation(reservation);
				
			} catch (AppException e) {
				e.printStackTrace();
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}	
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

	}
	
	
	
	@PUT
	@Path("updateReservation/{id}/{emailId}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Update Reservation",
					notes = "Update an existing reservation by specifying the confirmation number "
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Reservation updateReservation(@PathParam ("id") int confNo, Reservation reservation, @PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			
			ReservationDAO reservationDAO = new ReservationDAO();
			
			try {
				return reservationDAO.updateExistingReservation(confNo, reservation);
			} catch (AppException e) {
				e.printStackTrace();
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}
	
	
	@PUT
	@Path("cancelReservation/{id}/{emailId}/{token}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Cancel Reservation",
					notes = "Cancel a specific reservation by specifying a confirmation number"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})
	public Reservation cancelReservation(@PathParam ("id") int confNo, Reservation reservation, @PathParam("emailId") String emailId, @PathParam("token") String token) {
		
		
		if(AdminDAO.isSessionValid(emailId, token)) {
			
			ReservationDAO reservationDAO = new ReservationDAO();
			
			try {
				reservation.setReservationStatus("Cancelled");
				return reservationDAO.cancelExistingReservation(confNo, reservation);
			} catch (AppException e) {
				e.printStackTrace();
				throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			}
			
		} else {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	
	}
	
}

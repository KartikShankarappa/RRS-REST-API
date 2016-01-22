package com.kartik.rest.controllers;

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

import com.kartik.dao.ReservationDAO;
import com.kartik.dao.TableDAO;
import com.kartik.entities.Reservation;
import com.kartik.exceptions.AppException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Path("/reservations")
@Api(tags = {"Reservations"})
public class ReservationController {
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Find One Reservations",
					notes = "Find a reservation by specifying the confirmation number in the system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})	
	public Reservation findOneReservation(@PathParam ("id") int confNo) {
	
		try {
			ReservationDAO reservationDAO = new ReservationDAO();
			return reservationDAO.listOneReservation(confNo);
		} catch (AppException e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@POST
	@Path("/makeReservation")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Make Reservation",
					notes = "Make a new Reservation"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})	
	public Reservation createReservation(Reservation reservation) {
		
		try {

			ReservationDAO reservationDAO = new ReservationDAO();
			TableDAO tableDAO = new TableDAO();
	

			/*
			 * This method will try to find an available table and if available, then it will assign
			 * the tableNumber variable of the reservation object to the table number found. 
			 * If found, then the reservationStatus is set to "Confirmed".
			 * If no table is available, then the reservationStatus is set to "Waiting".
			 */
			int tableNo = tableDAO.findTable(reservation);
						
			if(tableNo != -1) {
				reservation.setTableNumber(tableNo);
				reservation.setReservationStatus("CONFIRMED");
			} else {
				reservation.setReservationStatus("WAITING");
			}
			return reservationDAO.createNewReservation(reservation);
		} catch (AppException e) {
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PUT
	@Path("updateReservation/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Update Reservation",
					notes = "Update a reservation by specifying the confirmation number in the system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})	
	public Reservation updateReservation(@PathParam ("id") int confNo, Reservation reservation) {
		
		ReservationDAO reservationDAO = new ReservationDAO();
		
		try {
			return reservationDAO.updateExistingReservation(confNo, reservation);
		} catch (AppException e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	@PUT
	@Path("cancelReservation/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation (value = "Cancel Reservation",
					notes = "Cancel a reservation by specifying the confirmation number in the system"
			)
	@ApiResponses (value = {
			@ApiResponse (code = 200, message = "Success"),
			@ApiResponse (code = 400, message = "Bad Request"),
			@ApiResponse (code = 401, message = "Unauthorised Access"),
			@ApiResponse (code = 404, message = "Not Found"),
			@ApiResponse (code = 500, message = "Internal Server Error")
	})	
	public Reservation deleteReservation(@PathParam ("id") int confNo, Reservation reservation) {
		
		ReservationDAO reservationDAO = new ReservationDAO();
		
		try {
			reservation.setReservationStatus("Cancelled");
			return reservationDAO.cancelExistingReservation(confNo, reservation);
		} catch (AppException e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
		}
		
	}
	
}

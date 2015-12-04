package com.kartik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.kartik.entities.Reservation;
import com.kartik.exceptions.AppException;
import com.kartik.utils.DBUtils;

public class ReservationDAO {

	/**
	 * TO list all the reservations
	 * @return
	 * @throws AppException
	 */
	public List<Reservation> listAllReservations() throws AppException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		
		try {
			ps = con.prepareStatement("select * from reservation");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Reservation reservation =  mapToReservationEntity(rs);
				reservations.add(reservation);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			
			DBUtils.closeResources(con, ps, rs);
		}
		return reservations;
	}
	
	/**
	 * To list one specific reservation
	 * @param confirmationNumber
	 * @return
	 * @throws AppException
	 */
	public Reservation listOneReservation(int confirmationNumber) throws AppException {
		Reservation reservation = null;
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement("select * from reservation where confNo = ?");
			ps.setInt(1, confirmationNumber);
			rs = ps.executeQuery();
		
			if(rs.next()) {
				reservation =  mapToReservationEntity(rs);
			}
		} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}

		return reservation;		
				
	}
	
	/**
	 * To make a new Reservation
	 * @param reservation
	 * @return
	 * @throws AppException
	 */
	public Reservation createNewReservation(Reservation reservation) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement("INSERT INTO reservation(tableNo, fname, lname, emailId, phoneNo, date, time, partySize, reservationStatus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, reservation.getTableNumber());
			ps.setString(2, reservation.getFirstName());
			ps.setString(3, reservation.getLastName());
			ps.setString(4, reservation.getEmailId());
			ps.setString(5, reservation.getPhoneNumber());
			ps.setString(6, reservation.getDate());
			ps.setString(7, reservation.getTime());
			ps.setInt(8, reservation.getPartySize());
			ps.setString(9, reservation.getReservationStatus());
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			
			if(rs.next()) {
				reservation.setConfirmationNumber(rs.getInt(1));
			}
		} catch (SQLException e) {
				e.printStackTrace();
				throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}

		return reservation;
	}
	
	
	/**
	 * To update existing reservation
	 * @param confId
	 * @param reservation
	 * @return
	 * @throws AppException 
	 */
	public Reservation updateExistingReservation(int confNo, Reservation reservation) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement("UPDATE reservation "
					+ "set fname = ?, lname = ?, emailId = ?, phoneNo = ?, date = ?, time = ?, partySize = ? where confNo = ?");
			
			ps.setString(1, reservation.getFirstName());
			ps.setString(2, reservation.getLastName());
			ps.setString(3, reservation.getEmailId());
			ps.setString(4, reservation.getPhoneNumber());
			ps.setString(5, reservation.getDate());
			ps.setString(6, reservation.getTime());
			ps.setInt(7, reservation.getPartySize());
			ps.setInt(8, confNo);
			
			ps.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		return reservation;
		
	}
	
	/**
	 * To delete/cancel an existing reservation
	 * @param confNo
	 * @param reservation
	 * @return
	 * @throws AppException 
	 */
	public Reservation cancelExistingReservation(int confNo, Reservation reservation) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = con.prepareStatement("UPDATE reservation SET reservationStatus = 'CANCELLED'  where confNo = ?; ");
			ps.setInt(1, confNo);
			
			ps.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		return reservation;		
	}

	
	
	
	
	/**
	 * A static method to map reservation details present in the result set to the reservation entity
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static Reservation mapToReservationEntity(ResultSet rs) throws SQLException {
		Reservation reservation = new Reservation();
		
		reservation.setConfirmationNumber(rs.getInt("confNo"));
		reservation.setTableNumber(rs.getInt("tableNo"));
		reservation.setFirstName(rs.getString("fname"));
		reservation.setLastName(rs.getString("lname"));
		reservation.setEmailId(rs.getString("emailId"));
		reservation.setPhoneNumber(rs.getString("phoneNo"));
		reservation.setPartySize(rs.getInt("partySize"));
		reservation.setDate(rs.getString("date"));
		reservation.setTime(rs.getString("time"));
		reservation.setReservationStatus(rs.getString("reservationStatus"));
	
		return reservation;
	}







}

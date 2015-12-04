package com.kartik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kartik.entities.Reservation;
import com.kartik.entities.ReservationTable;
import com.kartik.exceptions.AppException;
import com.kartik.utils.DBUtils;

public class TableDAO {

	public int findTable(Reservation reservation) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int tableNo = -1;
		
		try {
			ps = con.prepareStatement("Select * from reservationtable where availability = '1' and maxOccupancy >= ? ");
			ps.setInt(1, reservation.getPartySize());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				tableNo = rs.getInt("tableNo");
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		return tableNo;
	}

	public List<ReservationTable> findAllTables() throws AppException {

		List<ReservationTable> reservationTables = new ArrayList<ReservationTable>();
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ReservationTable reservationTable = new ReservationTable();
		
		try {
			ps = con.prepareStatement("SELECT * from reservationtable");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				reservationTable = mapToReservationTableEntity(rs);
				reservationTables.add(reservationTable);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		return reservationTables;
	}
	
	
	
	public static ReservationTable mapToReservationTableEntity(ResultSet rs) throws SQLException {
		
		ReservationTable reservationTable = new ReservationTable();
		
		reservationTable.setTableNumber(rs.getInt("tableNo"));
		reservationTable.setMaxOccupancy(rs.getInt("maxOccupancy"));
		reservationTable.setAvailability(rs.getBoolean("availability"));
		
		return reservationTable;
		
	}
}

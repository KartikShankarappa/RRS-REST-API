package com.kartik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kartik.entities.Restaurant;
import com.kartik.exceptions.AppException;
import com.kartik.utils.DBUtils;

public class RestaurantDAO {
	
	public Restaurant findRestaurantDetails() throws AppException {
		
		Restaurant restaurant  = new Restaurant();
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("select * from restaurant where id = 1");
			rs = ps.executeQuery();
			
			if(rs.next()) {
				restaurant.setRestaurantName(rs.getString("restaurantName"));
				restaurant.setEmailId(rs.getString("emailId"));
				restaurant.setAddress(rs.getString("address"));
				restaurant.setCity(rs.getString("city"));
				restaurant.setState(rs.getString("state"));
				restaurant.setZip(rs.getInt("zip"));
				restaurant.setPhoneNumber(rs.getString("phoneNumber"));
				restaurant.setDaysOfOperation(rs.getString("daysOfOperation"));
				restaurant.setOpeningTime(rs.getString("openingTime"));
				restaurant.setClosingtime(rs.getString("closingTime"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			
			DBUtils.closeResources(con, ps, rs);
		}
		return restaurant;
	}
	
	
	
	public Restaurant updateRestaurantDetails(Restaurant restaurant) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("UPDATE restaurant set restaurantName = ?, emailId = ?, address = ?,  city = ?, "
					+ "state = ?, zip = ?, phoneNo = ? daysOfOperation = ?, openingTime = ?, closingTime = ? where restaurantId = 1");
			
			ps.setString(1, restaurant.getRestaurantName());
			ps.setString(2, restaurant.getEmailId());
			ps.setString(3, restaurant.getAddress());
			ps.setString(4, restaurant.getCity());
			ps.setString(5, restaurant.getState());
			ps.setInt(6, restaurant.getZip());
			ps.setString(7, restaurant.getPhoneNumber());
			ps.setString(8, restaurant.getOpeningTime());
			ps.setString(9, restaurant.getOpeningTime());
			ps.setString(10, restaurant.getClosingtime());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		return restaurant;
		
		
	}

}

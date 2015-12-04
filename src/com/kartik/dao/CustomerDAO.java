package com.kartik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kartik.entities.Customer;
import com.kartik.exceptions.AppException;
import com.kartik.utils.DBUtils;

public class CustomerDAO {

	public List<Customer> listAllCustomers() throws AppException {

		List<Customer> customers = new ArrayList<Customer>();
		Customer customer = new Customer();
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement("SELECT * from customer");
			rs = ps.executeQuery();
			while (rs.next()) {
				customer = mapToCustomerEntity(rs);
				customers.add(customer);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}
		
		return customers;
	}
	
	
	
	public static Customer mapToCustomerEntity(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		
		customer.setCustomerId(rs.getInt("custId"));
		customer.setFname(rs.getString("fname"));
		customer.setLname(rs.getString("lname"));
		customer.setEmailId(rs.getString("emailId"));
		customer.setPhoneNumber(rs.getString("phoneNo"));
		
		return customer;
	}
	
}

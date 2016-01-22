package com.kartik.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.kartik.entities.Admin;
import com.kartik.exceptions.AppException;
import com.kartik.utils.DBUtils;

public class AdminDAO {

	public static Map<String, String> sessionData = new HashMap<String, String>();
	
	/**
	 * A method to authenticate the user
	 * @param loginDetails
	 * @return
	 * @throws AppException
	 */
	public Admin authenticateUser(Admin loginDetails) throws AppException {
		
		Connection con = DBUtils.Connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean authenticationStatus = false;
		String token = null;
		
		try {
			ps = con.prepareStatement("SELECT * from admin where emailId = ? and password = ?");
			ps.setString(1, loginDetails.getEmailId());
			ps.setString(2, loginDetails.getPassword());
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				authenticationStatus = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException(e.getMessage());
		} finally {
			DBUtils.closeResources(con, ps, rs);
		}
		
		if(authenticationStatus == true) {
			
			token = UUID.randomUUID().toString();
			
			loginDetails.setPassword(null);
			loginDetails.setToken(token);
			sessionData.put(loginDetails.getEmailId(), loginDetails.getToken());	
		} else {
			loginDetails.setToken(null);
		}
		
		return loginDetails;
	}

	/**
	 * A method to logout
	 * @param loginDetails
	 * @return
	 */
	public Admin logout(Admin loginDetails) {
		
		sessionData.remove(loginDetails.getEmailId());
		loginDetails.setEmailId(null);
		loginDetails.setToken(null);
		return loginDetails;
	}
	
	/**
	 * A method to check if the session is valid or not
	 * @param emailId
	 * @param token
	 * @return
	 */
	public static boolean isSessionValid(String emailId, String token) {
		
		String tempToken = AdminDAO.sessionData.get(emailId);
		
		if(tempToken != null && tempToken == token) {
			return true;
		} else {
			return false;
		}
	}

}

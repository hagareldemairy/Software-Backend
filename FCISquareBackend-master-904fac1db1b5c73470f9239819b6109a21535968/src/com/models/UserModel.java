package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	private int followid;
	private int userId;
	private int placeId;
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}
	
	public Integer getfollowid() {
		return followid;
	}
	
	
	public void setfollowid(int followid) {
		this.followid =followid;
	}

	public int getuserId() {
		return userId;
	}

	public void setuserId(int userId) {
		this.userId =userId;
	}
	
	public int getplaceId() {
		return userId;
	}

	public void setplaceId(int userId) {
		this.userId =userId;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static UserModel addFollowing(int followid, int userId) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into follow (`followingid`,`userid`) VALUES  (?,?)";
			 System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, followid);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		//	if (rs.next()) {
				UserModel user = new UserModel();
				user.userId = userId;
				user.followid = followid;
				return user;
			//}
			//return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static UserModel deleteFollowing(int followid, int userId) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "DELETE FROM follow WHERE `followingid`=? And `userid` =?;";
			 //System.out.println(sql);
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, followid);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
		//	if (rs.next()) {
				UserModel user = new UserModel();
				user.userId = userId;
				user.followid = followid;
				return user;
			//}
			//return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static UserModel getfollow(Integer userid) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from follow where `userid` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			//stmt.getDouble(1, lat);
			//stmt.getDouble(2, lon);
			stmt.setInt(1, userid);
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				//user.userid = rs.getInt(1);
				user.followid = rs.getInt("followingid");
				user.userId = rs.getInt("userid");
				return user;
			}
			return null;
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return null;
	}


	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}


public static UserModel Position(Integer id) {
	try{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "SELECT `lat` , `long` FROM users where `id` = ?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		//stmt.getDouble(1, lat);
		//stmt.getDouble(2, lon);
		stmt.setInt(1, id);
		
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			UserModel user = new UserModel();
			user.lat = rs.getDouble("lat");
			user.lon = rs.getDouble("long");
			return user;
		}
		return null;
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}



public static UserModel addPlace(int placeId, int userId) {
	try {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into savedplaces (`placeid`,`userid`) VALUES  (?,?)";
		 System.out.println(sql);

		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, placeId);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
	//	if (rs.next()) {
			UserModel user = new UserModel();
			user.userId = userId;
			user.placeId = placeId;
			return user;
		//}
		//return null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}

}
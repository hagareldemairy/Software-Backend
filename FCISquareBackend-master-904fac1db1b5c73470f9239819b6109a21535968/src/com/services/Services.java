package com.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;

import com.models.DBConnection;
import com.models.UserModel;

@Path("/")

public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */
	
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("pass", user.getPass());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id), Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_PLAIN)
	public String follow(@FormParam("followid") int followid,@FormParam("userid") int userId) {
		UserModel user = UserModel.addFollowing(followid, userId);
		JSONObject json = new JSONObject();
		json.put("followingid", user.getfollowid());
		json.put("userid", user.getuserId());
		
		return json.toJSONString();
	}
	
	
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String unfollow(@FormParam("followid") int followid,@FormParam("userid") int userId) {
		UserModel user = UserModel.deleteFollowing(followid, userId);
		JSONObject json = new JSONObject();
		json.put("followingid", user.getfollowid());
		json.put("userid", user.getuserId());
		
		return json.toJSONString();
	}
	
	@POST
	@Path("/getfollow")
	@Produces(MediaType.TEXT_PLAIN)
	public String getfollow(@FormParam("userid") int userId) {
		UserModel user = UserModel.getfollow(userId);
		JSONObject json = new JSONObject();
		json.put("id", user.getuserId());
		json.put("followingid", user.getfollowid());
		//json.put("lat", user.getLat());
		//json.put("long", user.getLon());
		return json.toJSONString();
	}

	
	@POST
	@Path("/getposition")
	@Produces(MediaType.TEXT_PLAIN)
	public String Position(@FormParam("id") int userId) {
		UserModel user = UserModel.Position(userId);
		JSONObject json = new JSONObject();
		json.put("id", user.getuserId());
		//json.put("followingid", user.getfollowid());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		return json.toJSONString();
	}
	
	
	@POST
	@Path("/saveplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String addPlace(@FormParam("placeid") int placeId,@FormParam("userid") int userId) {
		UserModel user = UserModel.addPlace(placeId, userId);
		JSONObject json = new JSONObject();
		json.put("placeid", user.getplaceId());
		json.put("userid", user.getuserId());
		
		return json.toJSONString();
	}
	
	
	
	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String getJson() {
		return "Hello after editing";
		// Connection URL:
		// mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/
	}
}

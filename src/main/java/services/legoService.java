package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import conn.Connections;
import conn.CurrentStatus;
import conn.Robot_Settings;

/*
 * URI to this service's Root Resource class is /laptopservice
 * The whole URL is http://some.server.someplace/rest/laptopservice
 * where some.server.someplace is the address to the server's web server (e.g. localhost:8080)
 * rest comes from the project web.xml file (element url-pattern)
 * laptopservice is the value of @Path below
 */
@Path("/legoservice")
public class legoService {

	/*
	 * This method can be reached with a GET (annotation @GET) type request to the
	 * URL http://some.server.someplace/rest/laptopservice/servicename ...just
	 * adding value of the @Path to the previously mentioned address Method can
	 * return plain text (@Produces...)
	 */
	@GET
	@Path("/setvalues")
	@Produces(MediaType.TEXT_PLAIN)
	public String setValues() {
		Connection conn = null;
		try {
			conn = Connections.getConnection();
			System.out.print("Connection 1:" + conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Using normal Prepared statement to add the values into the database
		try {
			System.out.print("Connection 2:" + conn);
			// Using common statement while reading, because there are no variables in the
			// sql statement
			Statement stmt = (Statement) conn.createStatement();
			ResultSet RS = stmt.executeQuery("select * from robot_settings");
			while (RS.next()) {
				Robot_Settings robot = new Robot_Settings();
				robot.setId(RS.getInt("id"));
				robot.setSpeed(Long.parseLong(RS.getString("speed")));
				robot.setDirection((RS.getBoolean("direction")));
				return robot.getParseString();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Before the function ends, the connection should be closed
		// This closing just returns the connection to the connection pool
		finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "";

	}

	@GET
	@Path("/retriveInfo")
	@Produces(MediaType.TEXT_PLAIN)
	public String retiveInfo() {
		return CurrentStatus.speed + "#" + CurrentStatus.isRunning;

	}

	@GET
	@Path("/retrivesettingsfromrobot")
	@Produces(MediaType.TEXT_PLAIN)
	public String sendvalues(@QueryParam("speed") String value, @QueryParam("isrunning") Boolean isrunning) {
		System.out.print("Speed: " + value + " Direction :" + isrunning);
		CurrentStatus.speed = Float.valueOf(value);
		CurrentStatus.isRunning = isrunning;
		return CurrentStatus.speed + "#" + CurrentStatus.isRunning;
	}

	@GET
	@Path("/updatespeed/{value}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatespeed(@PathParam("value") int value) {
		Connection conn = null;
		try {
			conn = Connections.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Using normal Prepared statement to add the values into the database
		try {

			String query = ("UPDATE robot_settings SET speed=?");
			PreparedStatement pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "" + value);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Before the function ends, the connection should be closed
		// This closing just returns the connection to the connection pool
		finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "Successfully-Saved";
	}

	/*
	 * This method can be reached with a GET (annotation @GET) type request to the
	 * URL http://some.server.someplace/rest/laptopservice/getonelaptop/someid where
	 * someid must be an integer. The method receives the path parameter by
	 * {laptopid} and is cast to int in method's actual parameter
	 * list @PathParam("laptopid") int id.
	 * 
	 * The implementation of this method is just to get one string from the method
	 * getLaptop, which cannot be reached directly by http URL.
	 */

	// @GET
//	@Path("/setspeed/{laptopid}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getOneLaptop(@PathParam("laptopid") int id) {
//		String s=getLaptop(id);
//		return s;
//	}

//	@GET
//	@Path("/setdirection/{laptopid}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String getOneLaptop(@PathParam("laptopid") int id) {
//		String s=getLaptop(id);
//		Connections.getConnection().
//		return s;
//	}
//}
}
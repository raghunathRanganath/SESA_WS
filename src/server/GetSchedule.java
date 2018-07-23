package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetSchedule extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

		String scheduleId = request.getParameter("sid");
				
		System.out.println(scheduleId);

		out.writeChars(getScheduleDetails(scheduleId));

		out.close();
	}

	private String getScheduleDetails(String scheduleId) {
		String physician_name = "";
		String specialty_name = "";
		String patient_name = "";
		String schedule_date = "";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");			
			
			PreparedStatement ps = con
					.prepareStatement(" select physician_name, specialty_name, patient_name, schedule_date \r\n " + 
							"  from master_schedule \r\n " + 
							" where schedule_id = ?");
			
			ps.setString(1, scheduleId);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				physician_name = rs.getString(1);
				specialty_name = rs.getString(2);
				patient_name = rs.getString(3);
				schedule_date = rs.getString(4);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return physician_name + ";" + specialty_name + ";" + patient_name + ";" + schedule_date;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}

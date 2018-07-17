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

public class CreateSchedule extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

		String patientName = request.getParameter("pname");
		String patientId = request.getParameter("pid");
		String physicianName = request.getParameter("physicianName");
		String specialtyName = request.getParameter("specialtyName");
		String scheduleDate = request.getParameter("scheduleDate");
		String comments = request.getParameter("comments");
				
		System.out.println(patientName);
		System.out.println(patientId);
		System.out.println(physicianName);
		System.out.println(specialtyName);
		System.out.println(scheduleDate);
		System.out.println(comments);

		if (confirmSchedule(patientName, patientId, physicianName, specialtyName, scheduleDate, comments)) {
			String schduleID = getCreatedScheduleID();
			out.writeObject(schduleID);
		} else {
			out.writeObject("Schedule cannot be created");
		}

		out.close();
	}

	private String getCreatedScheduleID() {
		String scheduleID = "";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");			
			
			PreparedStatement ps = con
					.prepareStatement(" SELECT max(SCHEDULE_ID)\r\n " + 
							"  FROM master_schedule\r\n ");

			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				scheduleID = rs.getString(1);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return scheduleID;
	}

	private boolean confirmSchedule(String patientName, String patientId, String physicianName, String specialtyName, String scheduleDate, String comments) {
		boolean status = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "sesa_dba", "sesa123");

			String sqlQuery = " insert into master_schedule(SCHEDULE_ID, PATIENT_ID, PATIENT_NAME, PHYSICIAN_NAME, SPECIALTY_NAME, SCHEDULE_DATE, COMMENTS) values( " +
			" schdeule_sequence.nextval, " +
			" ?, ?, ";
			if(!physicianName.equals("")) {
				sqlQuery = sqlQuery + " ?, ?, ";
			} else {
				sqlQuery = sqlQuery + " ?, ?, ";
			}
			sqlQuery = sqlQuery + " to_date(?,'dd/mm/yyyy hh24:mi:ss') ";
			sqlQuery = sqlQuery + " ,? ) ";
			
			PreparedStatement ps = con
					.prepareStatement(sqlQuery);
			ps.setString(1, patientId);
			ps.setString(2, patientName);
			ps.setString(3, physicianName);
			ps.setString(4, specialtyName);
			ps.setString(5, scheduleDate);
			ps.setString(6, comments);

			ResultSet rs = ps.executeQuery();
			status = rs.next();

		} catch (Exception e) {
			System.out.println(e);
		}
		return status;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

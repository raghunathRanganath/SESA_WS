package server;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmergencyService extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        ObjectOutputStream out = new ObjectOutputStream(response.getOutputStream());

        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");
        System.out.println(latitude);
        System.out.println(longitude);

        try {
            if (alertHospital(latitude, longitude)) {
                out.writeObject("SESA-001");

            } else {
                out.writeObject("SESA-009");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        out.close();
    }

    private boolean alertHospital(String latitude, String longitude) throws NumberFormatException, URISyntaxException {
        boolean status = false;
        
        Properties prop = new Properties();
        
        try {
            //set the properties value
            prop.setProperty("lat", latitude);
            prop.setProperty("lon", longitude);
 
            //save properties to project root folder
            prop.store(new FileOutputStream("C:\\Java\\Projects\\SESA Hackathon\\eclipse_workspace\\SESA_WS\\WebContent\\mapCoordinates.js"), null);
 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
        File f = new File("C:\\Java\\Projects\\SESA Hackathon\\eclipse_workspace\\SESA_WS\\WebContent\\emergencyMap.html");
        URI u = f.toURI();
        String defaultZoom = "15z";
        String url = "";//"https://www.google.co.in/maps/@" + Double.parseDouble(latitude) + "," + Double.parseDouble(longitude) + defaultZoom;
//        URI alertURI = new URI(u);
        try {
            java.awt.Desktop.getDesktop().browse(u);

        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

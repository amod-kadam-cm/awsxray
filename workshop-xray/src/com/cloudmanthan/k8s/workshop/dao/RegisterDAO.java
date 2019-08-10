package com.cloudmanthan.k8s.workshop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Subsegment;
import com.cloudmanthan.k8s.workshop.model.RegistrationInfo;

public class RegisterDAO {

	Logger logger = Logger.getLogger(this.getClass().getName());
	
	String db_host = System.getenv("DB_HOST");
	String db_port = System.getenv("DB_PORT");
	String db_name = System.getenv("DB_NAME");
	String db_user = System.getenv("DB_USER");
	String db_password = System.getenv("DB_PASSWORD");
	String driverClassName = "com.mysql.jdbc.Driver";
	String conString = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_name ;


	public boolean register(RegistrationInfo regInfo) {
		
		// Wrap in subsegment
	    Subsegment subsegment = AWSXRay.beginSubsegment("## RegisterDAO.register");
		boolean success = false;

		logger.log(Level.INFO, regInfo.toString());
		
	    AWSXRay.getCurrentSegment().putAnnotation("firstname", regInfo.getFirstName());
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			logger.log(Level.INFO, conString);
			//Connection con = DriverManager.getConnection(conString, db_user, db_password);
			
			Connection con = getPooledConnection();

			PreparedStatement stmt = con.prepareStatement("insert into registration values(?,?)");
			stmt.setString(1, regInfo.getFirstName());// 1 specifies the first parameter in the query
			stmt.setString(2, regInfo.getLastName());

			int i = stmt.executeUpdate();
			System.out.println(i + " records inserted");

			success = true ;
			con.close();

		} catch (Exception e) {
			subsegment.addException(e);
			System.out.println(e);
			logger.log(Level.SEVERE, e.getMessage());
			
		}finally {
		      AWSXRay.endSubsegment();
	    }
		logger.log(Level.INFO, "Sending Message");
		
		SendSNSNotification.sendMessage(regInfo);
		logger.log(Level.INFO, "Message Sent");
			
		
		
		
		return  success ;

	}

	private Connection getPooledConnection() throws SQLException {
		
		// Using JDBC Connection from Connection Pool provided by tomcat : DataSource
        DataSource datasource = new DataSource();
        datasource.setUrl(conString);
        datasource.setUsername(db_user);
        datasource.setPassword(db_password);
        datasource.setDriverClassName(driverClassName);
        datasource.setJdbcInterceptors("com.amazonaws.xray.sql.mysql.TracingInterceptor;");
        return datasource.getConnection();

		
	}
}

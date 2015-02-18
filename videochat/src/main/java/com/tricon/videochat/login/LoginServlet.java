package com.tricon.videochat.login;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class LoginServlet extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String userName = request.getParameter("username");
		String passoword = request.getParameter("password");
		
		System.out.println("Username :"+userName+", Password: "+passoword);
		
		Connection conn = null;

		Statement st = null;
		ResultSet rs = null;
		
		try {
				Class.forName("oracle.jdbc.OracleDriver");
		
				conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.1.15:1521:xe", "spark", "spark");
				st = conn.createStatement();
			
				rs = st
					.executeQuery("SELECT EMAIL, PASSWORD FROM CHAT_USERS WHERE EMAIL= '"+userName+"' ");
			
				
			 if (rs.next()){
				 
				if (userName.equalsIgnoreCase(rs.getString("EMAIL")) && passoword.equalsIgnoreCase(rs.getString("PASSWORD"))){
					RequestDispatcher rd = request.getRequestDispatcher("/html/home.html");
					rd.forward(request, response);
				}
				
			}
			
		}
		catch (ClassNotFoundException c){
			
		}
		catch (Exception e){
			
		}
		finally{
			if (conn != null){
				try{
					conn.close();
				}
				catch (SQLException e){
					
				}
			}
			if (st != null){
				try {
					st.close();
				}
				catch (SQLException c){
					
				}
			}
			if (rs != null){
				try{
					rs.close();
				}
				catch (SQLException e){
					
				}
			}
			
		}
		

	}
}

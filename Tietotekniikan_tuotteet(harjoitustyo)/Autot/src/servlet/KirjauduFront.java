package servlet;

import java.io.BufferedReader;  
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/KirjauduFront")
public class KirjauduFront extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public KirjauduFront() {
        super();
        System.out.println("KirjauduFront.TutkiKirjauduFront()");     
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("KirjauduFront.doGet()");		
		HttpSession session = request.getSession(true);
		session.invalidate();
		response.sendRedirect("index.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("KirjauduFront.doPost()");
		String uid = request.getParameter("uid");		
		String pwd = request.getParameter("pwd");
		String sessionId="";
		//Etsitään JSESSIONID selaimen kekseistä
		Cookie[] requestCookies = request.getCookies();		
		for(Cookie c : requestCookies){
			if(c.getName().equals("JSESSIONID")){
				sessionId=c.getValue();		
			}		
		}
		//Suolataan salasana
		String suola="saippuaKAUPPIAS";
		String suolattuPwd=pwd+suola;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			 byte[] messageDigest = md.digest(suolattuPwd.getBytes());
			 BigInteger number = new BigInteger(1, messageDigest);
			 pwd = number.toString(16).toUpperCase();
		} catch (Exception e) {			
			e.printStackTrace();
		}
		System.out.println(pwd);
		//Otettaan yhteys backendin KirjauduBack-servlettiin ja kysyään löytyykö käyttäjää
		//String backUrl = "http://localhost:8080/AutotBack/KirjauduBack";
		String backUrl = "http://localhost:8080/Autot/KirjauduBack";
		URL obj = new URL(backUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();		
		con.setRequestMethod("POST");		
		String urlParam = "uid="+uid+"&pwd="+pwd+"&sid="+sessionId;		
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParam);
		wr.flush();
		wr.close();			
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer resp = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			resp.append(inputLine);
		}
		in.close();		
		PrintWriter out = response.getWriter();
	    response.setContentType("text/html");
	    //Jos käyttäjä löytyi on resp-muuttujassa käyttäjän nimi, muuten tyhjä
	    if(!resp.toString().equalsIgnoreCase("")) {
	    	HttpSession session = request.getSession(true);
	    	session.setAttribute("kayttaja", resp.toString());
	    	session.setAttribute("sessionId", sessionId);
	    	out.print(1);
	    }    
	}
}

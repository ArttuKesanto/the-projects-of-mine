package servlet;

import java.io.IOException; 
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;

@WebServlet("/KirjauduBack")
public class KirjauduBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public KirjauduBack() {
        super();
        System.out.println("KirjauduBack.TutkiKirjauduBack()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("KirjauduBack.doGet()");
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("KirjauduBack.doPost()");
		String uid = request.getParameter("uid");		
		String pwd = request.getParameter("pwd");	
		String sessionId = request.getParameter("sid");    	   	
		Dao dao=new Dao();
		String kayttaja=dao.kirjaudu(uid, pwd, sessionId);
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html"); 
	    out.print(kayttaja);
	}

}

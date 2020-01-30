package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;

@WebServlet("/SuljeIstunto")
public class SuljeIstunto extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public SuljeIstunto() {
        super();
        System.out.println("SuljeIstunto.SuljeIstunto()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SuljeIstunto.doGet()");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("SuljeIstunto.doPost()");
		String sessionId=request.getParameter("sessionId");	
		Dao dao = new Dao();
		dao.suljeIstunto(sessionId);
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); 
		out.print("{}");
	}

}

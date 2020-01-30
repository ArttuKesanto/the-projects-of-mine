package servlet;

import java.io.IOException;   
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;

@WebServlet("/PoistaAuto")
public class PoistaAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
    public PoistaAuto() {
        super();
        System.out.println("PoistaAuto.PoistaAuto()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PoistaAuto.doGet()");		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PoistaAuto.doPost()");
		String rekNo = request.getParameter("rekNo");
		String sessionId=request.getParameter("sessionId");	
		Dao dao = new Dao();
		PrintWriter out = response.getWriter();
	    response.setContentType("text/html"); 
		if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta			
			if(dao.poistaAuto(rekNo)){
				out.println(1); //Auton poisto onnistui
			}else {
				out.println(0); //Auton poisto epäonnistui
			}
		}else {
			out.println(0);	//Tietojen päivitys epäonnistui, koska istunto ei ole voimassa
		}
	}
}

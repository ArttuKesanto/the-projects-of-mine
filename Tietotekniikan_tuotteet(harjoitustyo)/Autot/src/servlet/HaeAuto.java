package servlet;

import java.io.IOException;   
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.Dao;
import model.Auto;

@WebServlet("/HaeAuto")
public class HaeAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public HaeAuto() {
        super();  
        System.out.println("HaeAuto.HaeAuto()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeAuto.doGet()");
		//Muutettavan auton tietojen haku
		String rekNo = request.getParameter("rekNo");	
		String sessionId=request.getParameter("sessionId");		
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html"); 
		Dao dao = new Dao();	
		if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta
			Auto auto = dao.etsiAuto(rekNo);		
			JSONObject JSON = new JSONObject();
			JSON.put("merkki", auto.getMerkki());
			JSON.put("malli", auto.getMalli());
			JSON.put("vuosi", auto.getVuosi());
			JSON.put("rekNo", auto.getRekNo());	
			String strJSON = JSON.toString();			
		    out.println(strJSON);	
		}else {
			out.println("{}");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeAuto.doPost()");		
	}
}

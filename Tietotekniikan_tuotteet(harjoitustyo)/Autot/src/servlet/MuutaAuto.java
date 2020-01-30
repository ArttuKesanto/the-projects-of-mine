package servlet;

import java.io.IOException; 
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import model.Auto;


@WebServlet("/MuutaAuto")
public class MuutaAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MuutaAuto() {
        super();
        System.out.println("MuutaAuto.MuutaAuto()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaAuto.doGet()");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaAuto.doPost()");
		String uusiRekNo = request.getParameter("uusiRekNo");	
		String vanhaRekNo = request.getParameter("vanhaRekNo");
		String merkki = request.getParameter("merkki");
		String malli = request.getParameter("malli");
		String sessionId=request.getParameter("sessionId");	
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html");
	    Dao dao = new Dao();
	    if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta
			try {
				int vuosi = Integer.parseInt(request.getParameter("vuosi"));
				Auto auto = new Auto(uusiRekNo, merkki, malli, vuosi);							
				if(dao.muutaAuto(auto, vanhaRekNo)) {
					 out.println(1);  //Tietojen päivitys onnistui	
				}else {
					 out.println(0);  //Tietojen päivitys epäonnistui	
				}   
			} catch (Exception e) {
				out.println(0);		  //Tietojen päivitys epäonnistui, koska vuosi ei ollut luku
			}
	    }else {
			out.println(0);	//Tietojen päivitys epäonnistui, koska istunto ei ole voimassa
		}
		
	}
}

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
// import model.Auto; - vanha rakenne.
import model.Tuote;
import dao.DaoTietotekniikka;

@WebServlet("/HaeTtTuote")
public class HaeTtTuote extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public HaeTtTuote() {
        super();  
        System.out.println("HaeTtTuote.HaeTtTuote()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeTtTuote.doGet()");
		//Muutettavan tietotekniikkatuotteen tietojen haku
		int tuote_id = Integer.parseInt(request.getParameter("tuote_id"));
		String sessionId=request.getParameter("sessionId");
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html"); 
		Dao dao = new Dao();
		DaoTietotekniikka daoTT = new DaoTietotekniikka();
		if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta
			Tuote tuote = daoTT.etsiTtTuote(tuote_id);
			JSONObject JSON = new JSONObject();
			JSON.put("tuote_id_nimi", tuote.getTuote_id_nimi());
			JSON.put("tuote_malli", tuote.getTuote_malli());
			JSON.put("tuote_id", tuote.getTuote_id());
			JSON.put("tuote_kuvaus", tuote.getTuote_kuvaus());
			JSON.put("tuote_julkaisuvuosi", tuote.getTuote_julkaisuvuosi());
			JSON.put("tuote_hinta", tuote.getTuote_hinta());
			//JSON.put("tuote_id", tuote.getTuote_id());	
			String strJSON = JSON.toString();			
		    out.println(strJSON);	
		}else {
			out.println("{}");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HaeTtTuote.doPost()");		
	}
}

package servlet;

import java.io.IOException;   
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
// import model.Auto; // Vanha rakenne.
import dao.DaoTietotekniikka;
import model.Tuote;


@WebServlet("/MuutaTtTuote")
public class MuutaTtTuote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public MuutaTtTuote() {
        super();
        System.out.println("MuutaTtTuote.MuutaAuto()");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaTtTuote.doGet()");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("MuutaTtTuote.doPost()");
		String tuote_id_nimi = request.getParameter("tuote_id_nimi");	
		//String vanhaTuote_id_nimi = request.getParameter("vanhaTuote_id_nimi");
		String tuote_malli = request.getParameter("tuote_malli");
		//int tuote_julkaisuvuosi = (Integer.parseInt(request.getParameter("tuote_julkaisuvuosi")));
		//int tuote_id_uusi = (Integer.parseInt(request.getParameter("tuote_id_uusi")));
		//int tuote_id_vanha = (Integer.parseInt(request.getParameter("tuote_id_vanha")));
		String tuote_kuvaus = request.getParameter("tuote_kuvaus");
		//double tuote_hinta = (Double.parseDouble(request.getParameter("tuote_hinta")));
		String sessionId=request.getParameter("sessionId");	
		PrintWriter out = response.getWriter(  ); 
	    response.setContentType("text/html");
	    Dao dao = new Dao();
	    DaoTietotekniikka daoTT = new DaoTietotekniikka();
	    if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta
			try {
				//int vuosi = Integer.parseInt(request.getParameter("vuosi"));
				int tuote_julkaisuvuosi = (Integer.parseInt(request.getParameter("tuote_julkaisuvuosi")));
				int tuote_id_uusi = (Integer.parseInt(request.getParameter("tuote_id_uusi")));
				int tuote_id_vanha = (Integer.parseInt(request.getParameter("tuote_id_vanha")));
				double tuote_hinta = (Double.parseDouble(request.getParameter("tuote_hinta"))); // Kokeillaan virheiden välttämiseksi PARSET try-lohkon sisällä.
				Tuote tuote = new Tuote(tuote_id_nimi, tuote_malli, tuote_julkaisuvuosi, tuote_id_uusi, tuote_kuvaus, tuote_hinta);
				//Auto auto = new Auto(uusiRekNo, merkki, malli, vuosi);							
				if(daoTT.muutaTtTuote(tuote, tuote_id_vanha)) {
					 out.println(1);  //Tietojen päivitys onnistui
					 System.out.println("Toimii /MuutaTtTuote.");
				}else {
					 out.println(0);  //Tietojen päivitys epäonnistui
					 System.out.println("Ei onnistu muuttaminen vanhalla id:llä.");
				}   
			}catch (Exception e) {
				out.println(0);		  //Tietojen päivitys epäonnistui, koska vuosi ei ollut luku
				System.out.println("Exception e MuutaTtTuote.");
			}
	    }else {
			out.println(0);	//Tietojen päivitys epäonnistui, koska istunto ei ole voimassa
			System.out.println("Session ID not defined.");
		}
		
	}
}

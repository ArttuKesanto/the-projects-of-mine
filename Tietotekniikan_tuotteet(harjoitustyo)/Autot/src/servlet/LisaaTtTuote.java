package servlet;

import java.io.IOException;  
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao; // Importataan sessionID:n takia, koska GIT - turha tehdä uusiksi omaan tietokantaan.
import dao.DaoTietotekniikka;
import model.Tuote;

/**
 * Servlet implementation class LisaaTtTuote
 */
@WebServlet("/LisaaTtTuote")
public class LisaaTtTuote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LisaaTtTuote() {
        super();
        System.out.println("/LisaaTtTuote");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("LisaaTtTuote.doGet()");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("LisaaTtTuote.doPost()");
		PrintWriter out = response.getWriter(  );
	    response.setContentType("text/html"); 
		String sessionId=request.getParameter("sessionId");
		Dao dao = new Dao();
		DaoTietotekniikka daoTT = new DaoTietotekniikka();
		System.out.println("Malliin asti onnistui.");
		if(dao.etsiIstunto(sessionId)) { //Jos tietoja lähettävän sivuston sessionid löytyy tietokannasta
			Tuote tuote = new Tuote();
			tuote.setTuote_id_nimi(request.getParameter("tuote_id_nimi"));
			tuote.setTuote_malli(request.getParameter("tuote_malli"));
			
			//tuote.setTuote_julkaisuvuosi(Integer.parseInt(request.getParameter("tuote_julkaisuvuosi")));
			//tuote.setTuote_id(Integer.parseInt(request.getParameter("tuote_id")));
			tuote.setTuote_kuvaus(request.getParameter("tuote_kuvaus"));
			
			System.out.println("Malliin asti onnistui.");
			//tuote.setTuote_hinta(Double.parseDouble(request.getParameter("tuote_hinta")));
			
			
			try {
				tuote.setTuote_julkaisuvuosi(Integer.parseInt(request.getParameter("tuote_julkaisuvuosi")));
				tuote.setTuote_hinta(Double.parseDouble(request.getParameter("tuote_hinta")));
				tuote.setTuote_id(Integer.parseInt(request.getParameter("tuote_id")));
					if(daoTT.lisaaTtTuote(tuote)) {
							out.print(1);
					} else {
							out.print(0);
					}
			} catch (Exception e) {
				out.println(0);  // Julkaisuvuosi täytyy olla vuosiluku!
			}
		
		
		
		}else {
			out.println(0);  // Session ID tarkistus.
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
}
			
			//tuote.setTuote_id(Integer.parseInt(request.getParameter("rekNo")));
			
			
			//tuote.setTuote_kuvaus(request.getParameter("rekNo"));
			
			
			//tuote.setTuote_hinta(Double.parseDouble(request.getParameter("rekNo")));
			
			
			
			//auto.setRekNo(request.getParameter("rekNo"));
			//auto.setMerkki(request.getParameter("merkki"));
			//auto.setMalli(request.getParameter("malli"));

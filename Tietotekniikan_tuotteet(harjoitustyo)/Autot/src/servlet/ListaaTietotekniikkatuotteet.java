package servlet;

import java.io.IOException; 
import java.util.ArrayList;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.DaoTietotekniikka;
import model.Tuote;

/**
 * Servlet implementation class ListaaTietotekniikkatuotteet
 */
@WebServlet("/ListaaTietotekniikkatuotteet")
public class ListaaTietotekniikkatuotteet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListaaTietotekniikkatuotteet() {
        super();
        System.out.println("/ListaaTietotekniikkatuotteet");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("ListaaTietotekniikkatuotteet.doGet()"); // Ilmoittaa konsoliin.
		// Talletetaan GET viestin mukana tullut parametri (viesti). Viesti on parametrina request-oliossa.
		PrintWriter out = response.getWriter();
		String searchParameter = request.getParameter("hakusana");
		// String searchParameter = request.getParameter("hakusana", "hakusana1", "hakusana2");
		System.out.println("hakusana: " + searchParameter);
		DaoTietotekniikka daoTT = new DaoTietotekniikka();
		// Tarkistetaan, onko käyttäjä antanut hakusanaa, joks ei ole antanut, annetaan arvoksi tyhjä merkki.
// if(dao.etsiIstunto(sessionId)) {
		if (searchParameter == null) {
			searchParameter = "";
			//System.out.println("hakusana: " + searchParameter);
		}
		// Nyt voidaan sitten kutsua DaoTietotekniikka-luokan metodia, jotta päästään ajamaan haku tietokantaan.
		ArrayList<Tuote> tietotekniikkaTuotteet = daoTT.listaaKaikki(searchParameter);
		
		String ttTuotteetJSON = new JSONObject().put("tietotekniikkatuotteet", tietotekniikkaTuotteet).toString(); // Konvertoi parametriin "tietotekniikkatuotteet"
		response.setContentType("text/html");
		// Lähetetään viestin mukana.
		out.println(ttTuotteetJSON);
//}else {
//	out.println("{}");
//}
	}

}

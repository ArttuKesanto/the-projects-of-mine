package servlet;

import java.io.IOException;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class VastaanotaViesti
 */
@WebServlet("/VastaanotaViesti")
public class VastaanotaViesti extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VastaanotaViesti() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// käynnistetään POST-viestin yhteydessä, doPost, automaattisesti.
		String nimi = request.getParameter("nimi");
		System.out.println("Nimi on " + nimi);//olion nimi on nimi lahetaviesti-osiossa.
	}

}

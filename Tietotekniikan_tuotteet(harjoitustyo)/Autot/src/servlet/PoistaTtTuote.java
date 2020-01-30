package servlet;

import java.io.IOException;   
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.DaoTietotekniikka;

/**
 * Servlet implementation class PoistaTtTuote
 */
@WebServlet("/PoistaTtTuote")
public class PoistaTtTuote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PoistaTtTuote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath()); Parempi tämän sijasta esim:
		System.out.println("PoistaTtTuote.doGet()");	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("PoistaTtTuote.doPost()");
		int tuote_id = Integer.parseInt(request.getParameter("tuote_id"));
		String sessionId=request.getParameter("sessionId");	
		Dao dao = new Dao();
		DaoTietotekniikka daoTT = new DaoTietotekniikka();
		PrintWriter out = response.getWriter();
	    response.setContentType("text/html"); 
		if(dao.etsiIstunto(sessionId)) { 		
			if(daoTT.poistaTtTuote(tuote_id)){
				out.println(1); // Tuotteen poisto ok.
			}else {
				out.println(0); // Tuotteen poista not ok.
			}
		}else {
			out.println(0);	// Tämä liittyy Dao:n istuntoihin, muut metodit daoTT-oliosta.
		}
	}
	}

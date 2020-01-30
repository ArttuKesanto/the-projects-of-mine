package servlet;

import java.io.IOException; 
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.Dao;
import model.Auto;

@WebServlet("/ListaaAutot")
public class ListaaAutot extends HttpServlet {
	private static final long serialVersionUID = 1L;
          
    public ListaaAutot() {
        super();
        System.out.println("ListaaAutot.ListaaAutot()");
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ListaaAutot.doGet()");
		String hakuSana = request.getParameter("hakuSana");	
		String sessionId=request.getParameter("sessionId");
		PrintWriter out = response.getWriter();
		Dao dao = new Dao();
		if(dao.etsiIstunto(sessionId)) { //Jos tietoja pyytävän sivuston sessionid löytyy tietokannasta
			if(hakuSana==null) {
				hakuSana="";
			}		
			ArrayList<Auto> autot = dao.listaaKaikki(hakuSana);
			String strJSON = new JSONObject().put("autot", autot).toString();			
		    response.setContentType("text/html"); 
		    out.println(strJSON);
		}else {
			out.println("{}");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ListaaAutot.doPost()");
	}

}

package dao;

import java.sql.Connection;   
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Auto;

public class Dao { 
	private Connection con=null; // private Connection con;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Autot.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//path += "/webapps/"; //Tuotannossa. Laita tietokanta webapps-kansioon
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public boolean lisaaAuto(Auto auto){
		boolean paluuArvo=true;
		sql="INSERT INTO autot VALUES(?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, auto.getRekNo());
			stmtPrep.setString(2, auto.getMerkki());
			stmtPrep.setString(3, auto.getMalli());
			stmtPrep.setInt(4, auto.getVuosi());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public ArrayList<Auto> listaaKaikki(){
		ArrayList<Auto> autot = new ArrayList<Auto>();
		sql = "SELECT * FROM autot";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Auto auto = new Auto();
						auto.setRekNo(rs.getString(1));
						auto.setMerkki(rs.getString(2));
						auto.setMalli(rs.getString(3));	
						auto.setVuosi(rs.getInt(4));	
						autot.add(auto);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return autot;
	}
	
	public ArrayList<Auto> listaaKaikki(String hakusana){
		ArrayList<Auto> autot = new ArrayList<Auto>();
		sql = "SELECT * FROM autot WHERE rekno LIKE ? or merkki LIKE ? or malli LIKE ?";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);  
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");   
				stmtPrep.setString(3, "%" + hakusana + "%");   
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui							
					while(rs.next()){
						Auto auto = new Auto();
						auto.setRekNo(rs.getString(1));
						auto.setMerkki(rs.getString(2));
						auto.setMalli(rs.getString(3));	
						auto.setVuosi(rs.getInt(4));	
						autot.add(auto);
					}					
				}				
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return autot;
	}
	
	public Auto etsiAuto(String rekNo){
		Auto auto = null;
		sql = "SELECT * FROM autot WHERE rekNo=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, rekNo);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
        			rs.next();
        			auto = new Auto();        			
        			auto.setRekNo(rs.getString(1));
					auto.setMerkki(rs.getString(2));
					auto.setMalli(rs.getString(3));	
					auto.setVuosi(rs.getInt(4));       			      			
				}        		
			}	
			con.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return auto;		
	}
	
	public boolean muutaAuto(Auto auto, String rekNo){
		boolean paluuArvo=true;
		sql="UPDATE autot SET rekNo=?, merkki=?, malli=?, vuosi=? WHERE rekNo=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, auto.getRekNo());
			stmtPrep.setString(2, auto.getMerkki());
			stmtPrep.setString(3, auto.getMalli());
			stmtPrep.setInt(4, auto.getVuosi());
			stmtPrep.setString(5, rekNo);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaAuto(String rekNo){ //Oikeassa elämässä tiedot ensisijaisesti merkitään poistetuksi.
		boolean paluuArvo=true;
		sql="DELETE FROM autot WHERE rekNo=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setString(1, rekNo);			
			stmtPrep.executeUpdate();
	        con.close();
		} catch (SQLException e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}	
	
	public String kirjaudu(String uid, String pwd, String sessionId){
		String paluuArvo="";
		sql="SELECT * FROM kayttajat WHERE email=? AND pwd=?";			
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, uid);
				stmtPrep.setString(2, pwd);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli käyttäjä löytyi         			
        			rs.next();
        			paluuArvo=rs.getString(2)+" "+rs.getString(3);	
        			//Lisätään istunnon id tietokantaan
        			sql="INSERT INTO Istunnot(sessionId) VALUES(?)";
        			stmtPrep=con.prepareStatement(sql); 
        			stmtPrep.setString(1, sessionId);			
        			stmtPrep.executeUpdate();        			       			
				}        		
			}	
			con.close();
			suljeVanhatIstunnot();
		} catch (Exception e) {
			e.printStackTrace();
		}				
		return paluuArvo;
	}	
	
	public boolean etsiIstunto(String sessionId){
		boolean paluu=false;
		sql = "SELECT * FROM istunnot WHERE sessionId=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, sessionId);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
        			paluu=true;        			       			
				}        		
			}	
			con.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return paluu;		
	}
	
	public void suljeIstunto(String sessionId){		
		sql = "DELETE FROM istunnot WHERE sessionId=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setString(1, sessionId);
        		stmtPrep.executeUpdate();        				
			}	
			con.close(); 			
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	private void suljeVanhatIstunnot(){		
		sql = "DELETE from istunnot WHERE date(timestamp) < date('now','-1 days')"; //Poistetaan kaikki eilistä vanhemmat istunnot      
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql);				
        		stmtPrep.executeUpdate();        				
			}	
			con.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
}

package dao;

import java.sql.Connection;    
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Importattu / kopioitu mallista, tarvitaan tässä luokassa.

// import model.Auto; -- myös vanha rakenne alkuperäisen DAO:n mallina olosta.

// import model.Auto; Vanha rakenne.

import model.Tuote; // Tämä omaan model-luokkaan.

public class DaoTietotekniikka { 
	// Yhteys-olio, yhteys tietokantaan, toimii myös esim. MariaDB:llä.
	private Connection con = null; 
	// private Connection con, mallista. Tietokantahaun tulos tähän olioon. Tauluta löydetyt tiedot.
	private ResultSet rs = null;
	// Olioon tietokannasta ajettava SQL-lause.
	// Estää SQL-injectionin - Sauli Iso.
	private PreparedStatement stmtPrep = null; 
	private String sql;
	// Tietokannan nimi.
	private String db = "Tietotekniikkatuotteet.db";
	
	private Connection yhdista(){ // sama koodi myös MariaDB; yhteys tietokantaan - Arttu K.
    	Connection con = null; 
    	// Java-projektin juuri, mistä tietokanta-tiedosto löytyy.
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//path += "/webapps/"; //Tuotannossa. Laita tietokanta webapps-kansioon. Replace \\ with /. Korvataan Windowsin hakemistoeroittimet "Internetin" hakemistoeroittimista.
    	//	// Tietokannan osoite:
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC"); // Otetaan ajurista luokka käyttöön. MariaDB voi korvata sqliten.
	        // Avataan yhteys tietokantaan.
    		con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui. Tarkista asetukset.");
	        e.printStackTrace();	         
	     }
	     return con; // Palauttaa yhteyden, jos on olemassa.
	}
	
		//public ArrayList<Tuote> listaaKaikki(String hakusana, int hakusana1, double hakusana2){
		//public ArrayList<Tuote> listaaKaikki(String hakusana, int hakusana1, double hakusana2){
	public ArrayList<Tuote> listaaKaikki(){
		ArrayList<Tuote> tuotteet = new ArrayList<Tuote>();
		sql = "SELECT * FROM Tuote";  
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui - luennoitsija.
				// Luodaan komento-olio, joka voidaan sitten ajaa tietokannassa.
				stmtPrep = con.prepareStatement(sql);
				// Talletetaan SQL-lauseeseen ?-merkkien tilalle arvot.
				//stmtPrep.setString(1, "%" + hakusana + "%");
				//stmtPrep.setString(2, "%" + hakusana + "%");   
				//stmtPrep.setString(3, "%" + hakusana + "%");
				//stmtPrep.setInt(4, hakusana1);
				//stmtPrep.setDouble(5, hakusana2);
				// Ajetaan SQL-lause tietokannassa.
        		rs = stmtPrep.executeQuery();
        		System.out.println("Haku tietokannasta onnistui.");
				if(rs!=null){ //jos kysely onnistui	
					// Kyselyssä tuli useita datarivejä, jotka kopioidaan ArrayList-tietorakenteeseen.
					while(rs.next()){
						Tuote tuote = new Tuote();
						tuote.setTuote_id_nimi(rs.getString(1));
						tuote.setTuote_malli(rs.getString(2));
						tuote.setTuote_julkaisuvuosi(rs.getInt(3));	
						tuote.setTuote_id(rs.getInt(4)); // Näytetään myös ID, mahdollisia muokkauksia myöhemmin.
						tuote.setTuote_kuvaus(rs.getString(5));
						tuote.setTuote_hinta(rs.getDouble(6));
						
						// Lisätään datarivi ArrayList tietorakenteeseen.
						tuotteet.add(tuote);
						System.out.println("Lisääminen onnistui."); // Varmistus itselle.
					}					
				
				}				
			}
			con.close();
		} catch (SQLException e) { // Voi ottaa SQL-etuliitteen pois.
			e.printStackTrace();
		}
		// Lähetetään datarivit kutsuneelle ohjelmalle, tässä Servletille.
		return tuotteet;
	}
	
	
	
		public ArrayList<Tuote> listaaKaikki(String hakusana){
		ArrayList<Tuote> tuotteet = new ArrayList<Tuote>();
		sql = "SELECT tuote_id_nimi, tuote_malli, tuote_julkaisuvuosi, tuote_id, tuote_kuvaus, tuote_hinta FROM Tuote WHERE tuote_id_nimi LIKE ? OR tuote_malli LIKE ? OR tuote_kuvaus LIKE ? or tuote_julkaisuvuosi = ? or tuote_hinta LIKE ?"; // ? tarkoittaa tässä ensimmäistä parametria, liittyy alempaan koodiin, ensimmäinen (1) tarkoittaa ensimmäistä kysymysmerkkiä. Hakulaajennusta...  Tällä hetkellä double ei toimi, eikä julkaisuvuosi. Vain STRING!    
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui - luennoitsija.
				// Luodaan komento-olio, joka voidaan sitten ajaa tietokannassa.
				stmtPrep = con.prepareStatement(sql);
				// Talletetaan SQL-lauseeseen ?-merkkien tilalle arvot.
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");   
				stmtPrep.setString(3, "%" + hakusana + "%");
				//stmtPrep.setInt(4, hakusana1);
				//stmtPrep.setDouble(5, hakusana2);
				// Ajetaan SQL-lause tietokannassa.
        		rs = stmtPrep.executeQuery();
        		System.out.println("Haku tietokannasta onnistui.");
				if(rs!=null){ //jos kysely onnistui	
					// Kyselyssä tuli useita datarivejä, jotka kopioidaan ArrayList-tietorakenteeseen.
					while(rs.next()){
						Tuote tuote = new Tuote();
						tuote.setTuote_id_nimi(rs.getString(1));
						tuote.setTuote_malli(rs.getString(2));
						tuote.setTuote_julkaisuvuosi(rs.getInt(3));	
						tuote.setTuote_id(rs.getInt(4)); // Näytetään myös ID, mahdollisia muokkauksia myöhemmin.
						tuote.setTuote_kuvaus(rs.getString(5));
						tuote.setTuote_hinta(rs.getDouble(6));
						
						// Lisätään datarivi ArrayList tietorakenteeseen.
						tuotteet.add(tuote);
						System.out.println("Lisääminen onnistui.");
					}					
				
				}				
			}
			con.close();
		} catch (SQLException e) { // Voi ottaa SQL-etuliitteen pois.
			e.printStackTrace();
		}
		// Lähetetään datarivit kutsuneelle ohjelmalle, tässä Servletille.
		return tuotteet;
	}
		
		
		public boolean lisaaTtTuote(Tuote ttTuote){
			boolean paluuArvo=true;
			sql="INSERT INTO Tuote VALUES(?,?,?,?,?,?)";	// Yksi kysymysmerkki poistettu, AutoIncrement käytössä tietokannassa tuote_id:lle. Primary Key.					  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, ttTuote.getTuote_id_nimi());
				stmtPrep.setString(2, ttTuote.getTuote_malli());
				stmtPrep.setInt(3, ttTuote.getTuote_julkaisuvuosi());
				stmtPrep.setInt(4, ttTuote.getTuote_id());
				stmtPrep.setString(5, ttTuote.getTuote_kuvaus());
				stmtPrep.setDouble(6, ttTuote.getTuote_hinta());
				stmtPrep.executeUpdate();
		        con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}
		
		public boolean poistaTtTuote(int tuote_id){ // Pakko muuttaa INT:iksi, koska INT-datatyyppi - Arttu K.
			boolean paluuArvo=true;
			sql="DELETE FROM Tuote WHERE tuote_id = ?";						  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setInt(1, tuote_id);			
				stmtPrep.executeUpdate();
		        con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}
		
		
		public Tuote etsiTtTuote(int tuote_id){
			Tuote tuote = null;
			sql = "SELECT * FROM Tuote WHERE tuote_id = ?";       
			try {
				con=yhdista();
				if(con!=null){ 
					stmtPrep = con.prepareStatement(sql); 
					stmtPrep.setInt(1, tuote_id);
	        		rs = stmtPrep.executeQuery();  
	        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
	        			rs.next();
	        			tuote = new Tuote();        			
	        			tuote.setTuote_id_nimi(rs.getString(1));
	    				tuote.setTuote_malli(rs.getString(2));
	    				tuote.setTuote_julkaisuvuosi(rs.getInt(3));
	    				tuote.setTuote_id(rs.getInt(4));
	    				tuote.setTuote_kuvaus(rs.getString(5));
	    				tuote.setTuote_hinta(rs.getDouble(6)); // Taulukko käydään läpi ja etsitään tiedot oikeille kohdilleen. - Arttu K.     			      			
					}        		
				}	
				con.close();  
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return tuote;		
		}
		
		
		public boolean muutaTtTuote(Tuote ttTuote, int tuote_id){
			boolean paluuArvo=true;
			sql="UPDATE Tuote SET tuote_id_nimi = ?, tuote_malli = ?, tuote_julkaisuvuosi = ?, tuote_id = ?, tuote_kuvaus = ?, tuote_hinta = ? WHERE tuote_id = ?";				  
			try {
				con = yhdista();
				stmtPrep=con.prepareStatement(sql); 
				stmtPrep.setString(1, ttTuote.getTuote_id_nimi());
				stmtPrep.setString(2, ttTuote.getTuote_malli());
				stmtPrep.setInt(3, ttTuote.getTuote_julkaisuvuosi());
				stmtPrep.setInt(4, ttTuote.getTuote_id());
				stmtPrep.setString(5, ttTuote.getTuote_kuvaus());
				stmtPrep.setDouble(6, ttTuote.getTuote_hinta());
				stmtPrep.setInt(7, tuote_id);
				stmtPrep.executeUpdate();
		        con.close();
			} catch (SQLException e) {				
				e.printStackTrace();
				paluuArvo=false;
			}				
			return paluuArvo;
		}


}

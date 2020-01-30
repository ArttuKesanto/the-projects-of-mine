<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Add JSP comment using the described tags around this text.
Add HTML comments using the <!-- --> tags. Deklaraatiot % ja ><    - Arttu K. --%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png">
<link rel="shortcut icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png">
   <%-- Add JSP comment using the described tags around this text.
	Add HTML comments using the <!-- --> tags. Deklaraatiot % ja >< - Arttu K. --%>
<title>Tietotekniikkatuotteen tietojen muutos</title>
</head>
<body>
<form id="tiedot">
<table class="table">
	<thead>
		<tr>
			<th colspan="8" class="oikealle"><span id="takaisin">Takaisin tuotelistaukseen</span></th>
		</tr>
		<tr>
			<th>Tuotteen nimi</th>
			<th>Tuotteen malli</th>
			<th>Tuotteen id (vrt. suurimpaan numeroon)</th>	
			<th>Tuotteen kuvaus</th>
			<th>Tuotteen julkaisuvuosi</th>	
			<th>Tuotteen hinta</th>
			<th>Hallintapaneeli</th>
		</tr>
	</thead>
		<tr>
			<td><input type="text" name="tuote_id_nimi" id="tuote_id_nimi"></td>
			<td><input type="text" name="tuote_malli" id="tuote_malli"></td>
			<td><input type="text" name="tuote_id_uusi" id="tuote_id_uusi"></td> 
			<td><input type="text" name="tuote_kuvaus" id="tuote_kuvaus"></td> 
			<td><input type="text" name="tuote_julkaisuvuosi" id="tuote_julkaisuvuosi"></td>
			<td><input type="text" name="tuote_hinta" id="tuote_hinta"></td>
			
			
		<%--	// Tämä tulee Hallintapaneeli-kohdan alle. --%>
			<td><input type="submit" id="Tallenna" value="Tallenna"></td>
		</tr>
		<tbody>
	</tbody>
	</table>
	<input type="hidden" name="tuote_id_vanha" id="tuote_id_vanha">
	<input type="hidden" name="sessionId" value="<%out.print(session.getAttribute("sessionId"));%>">
</form>
<span id="ilmo1"></span>
<script>
$(document).ready(function(){
	
	$("#tuote_id_uusi").focus();
	
	//Haetaan muutettavan tuotteen (tietotekniikka) tiedot
	var tuoteID = requestURLParam("tuote_id"); //Funktio löytyy scripts/main.js  - kuten muissakin .jsp-filuissa.	    
	$.getJSON(backPath+"HaeTtTuote", {tuote_id: tuoteID, sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result){	//Tiedot kulkevat palvelimelta GET-metodilla JSON-muodossa	
		$("#tuote_id_nimi").val(result.tuote_id_nimi);	
		$("#tuote_malli").val(result.tuote_malli);
		$("#tuote_id_uusi").val(result.tuote_id);
		$("#tuote_kuvaus").val(result.tuote_kuvaus);	
		$("#tuote_julkaisuvuosi").val(result.tuote_julkaisuvuosi);
		$("#tuote_hinta").val(result.tuote_hinta);
		// Done and done.
		$("#tuote_id_vanha").val(result.tuote_id);	
	
    });
		
	$("#takaisin").click(function(){
		document.location="listaaTietotekniikkatuotteet.jsp";
	});
		
	$("#tiedot").validate({						
		rules: {
			tuote_id_nimi:  {
				required: true,
				minlength: 5				
			},	
			tuote_malli:  {
				required: true,
				minlength: 5				
			},
			tuote_id_uusi:  {
				required: true,  
				minlength: 1,
				maxlength: 20
			}, 
			tuote_kuvaus:  {
				required: true,
				minlength: 5,
				maxlength: 2500
			},
			tuote_julkaisuvuosi:  {
				required: true,
				number: true,
				minlength: 4,
				maxlength: 4,
				min: 2012,
				max: new Date().getFullYear()+2 // Tuote tulossa markkinoille.
			},
			tuote_hinta:  {
				required: true,
				number: true
			}
		},
		messages: {
			tuote_id_nimi:  {
				required: "Puuttuu tiedot.", 
				minlength: "Liian lyhyt nimi."				
			},	
			tuote_malli:  {
				required: "Puuttuu tiedot.",
				minlength: "Liian lyhyt kuvaus."			
			},
			tuote_id_uusi:  {
				required: "Puuttuu.",
				minlength: "Vain numeroita.",
				maxlength: "Vain numeroita."
			},	
			tuote_kuvaus:  {
				required: "Puuttuu tiedot.",
				minlength: "Liian lyhyt kuvaus.",
				maxlength: "Liian pitkä kuvaus."
			},
			tuote_julkaisuvuosi:  {
				required: "Tiedot puuttuu",
				number: "Vain numeroita.",
				minlength: "Anna kunnollinen vuosi. Vuosi välillä 2012 - 2021.",
				maxlength: "Anna kunnollinen vuosi. Vuosi välillä 2012 - 2021.",
				min: "Anna kunnollinen vuosi. Vuosi välillä 2012 - 2021.",
				max: "Anna kunnollinen vuosi. Vuosi välillä 2012 - 2021."
			},
			tuote_hinta:  {
				required: "Hinta puuttuu.",
				number: "Vain numeroita. Vertaa suurimpaan edelliseen ja valitse seuraava."
			}
		},			
		submitHandler: function(form) {	
			vieTiedot();
		}		
	}); 
	});
function vieTiedot(){
	$.post(backPath+"MuutaTtTuote", $("#tiedot").serialize(), function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
        if(result==0){
        	$("#ilmo1").html("Tietojen päivitys epäonnistui. Ole erityisen tarkka tuotteen id -kentän kanssa. Ei voi olla sama kuin jo muulla tuotteella oleva ID.");
        }else if(result==1){
			document.location="listaaTietotekniikkatuotteet.jsp?tuote_id=" + $("#tuote_id_uusi").val();
		}
		else {
			$("#ilmo1").html("Tietojen päivitys epäonnistui. Ole tarkkana erityisesti \"tuotteen id\" -kentän kanssa.");
		}
    });
}
</script>
</body>
</html>
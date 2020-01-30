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
<title>Uuden tuotteen lisääminen</title>
</head>
<body>
<form action="LisaaTtTuote" id="tiedot">
<table>
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
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="tuote_id_nimi" id="tuote_id_nimi"></td>
			<td><input type="text" name="tuote_malli" id="tuote_malli"></td>
			<td><input type="text" name="tuote_id" id="tuote_id"></td> 
			<td><input type="text" name="tuote_kuvaus" id="tuote_kuvaus"></td> 
			<td><input type="text" name="tuote_julkaisuvuosi" id="tuote_julkaisuvuosi"></td>
			<td><input type="text" name="tuote_hinta" id="tuote_hinta"></td>
			
			
			
			<td><input type="submit" id="tallenna" value="Lisää"></td>
		</tr>
	</tbody>
</table>
<input type="hidden" name="sessionId" value="<%out.print(session.getAttribute("sessionId"));%>">
</form>
<span id="ilmo"></span>
<script>
$(document).ready(function(){
		
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
			tuote_id:  {
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
			tuote_id:  {
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
			lisaaTiedot();
		}		
	});   
	$("#tuote_id").focus();
	});
function lisaaTiedot(){
	$.post(backPath + "LisaaTtTuote", $("#tiedot").serialize(), function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
        if(result==0){
        	$("#ilmo").html("Tuotteen lisääminen epäonnistui. Kokeile uudestaan. Ole erityisen tarkka tuotteen id-numeroinnin kanssa. Tämä ei voi olla sama kuin jo listassa olevilla. Valitse eri numero.");
        }else if(result==1){
        	$("#ilmo").html("Tuotteen lisääminen onnistui. Palataan edelliselle sivulle.");
        	//document.location="listaaTietotekniikkatuotteet.jsp?tuote_id=" + $("#tuote_id").val();
        	  setTimeout(function(){
        		  document.location="listaaTietotekniikkatuotteet.jsp?tuote_id=" + $("#tuote_id").val();  //Poistetaan rivin värjäys aikaviiveellä							
					}, 3000); // Annetaan tekstin näkyä hetki yksinkertaisen timeout-funktion avulla. - Arttu K.
        	//document.location='listaaTietotekniikkatuotteet.jsp?tuote_id=' + $("#tuote_id").val();
			//$("#ilmo").html("Tuotteen lisääminen onnistui. Palaa edelliselle sivulle.");
		}
        else {
        	$("#ilmo").html("Tuotteen lisääminen onnistui. Palaa edelliselle sivulle.");
        	document.location="listaaTietotekniikkatuotteet.jsp?tuote_id=";
        }
    });	
}
</script>

</body>
</html>
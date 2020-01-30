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
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png">
<link rel="shortcut icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png"> 
   <%-- Add JSP comment using the described tags around this text.
	Add HTML comments using the <!-- --> tags. Deklaraatiot % ja >< - Arttu K. --%>
<title>Tietotekniikkatuotteiden listaus</title>
</head>
<span id="ilmo"></span>

<body>
	<%-- <div id = "taulukko"></div>
	Hakusana: <input type = "text" id = "hakusana" value = "Anna hakusana..."> --%>
	
	<table id="listaus" class="table">
	<thead>
		<tr>
			<th colspan="4"><%out.print(session.getAttribute("kayttaja"));%>&nbsp;<span id="logOut">Kirjaudu ulos</span></th>
			<th colspan="4" class="oikealle"><span id="uusiTietotekniikkatuote">Lisää uusi tietotekniikkatuote</span></th>
		</tr>
		<tr>
			<th colspan="4" class="oikealle">Hakusana:</th>
			<th colspan="4"><input type="text" id="hakuSana"><input type="button" id="hae" value="Hae tuotetta"></th>
		</tr>
		<tr>
			<th>Tuotteen nimi</th>
			<th>Tuotteen malli</th>
			<th>Tuotteen id</th>		
			<th>Tuotteen kuvaus</th>
			<th>Tuotteen julkaisuvuosi</th>	
			<th>Tuotteen hinta</th>
			<th>Hallintapaneeli</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>



	<script>
		<%--$().empty();
		$.getJSON("ListaaTietotekniikkatuotteet", {searchParameter: $("#hakusana").val()}, function(result) {
		}); --%>
		$(document).ready(function(){
			haeTiedot();
			
			$("#hakuSana").focus();
			
			//Tässä tulee käyttää document-tason kuuntelijaa, koska poista-luokan elementit on luotu jQuerylla ja suora sidonta sivulle puuttuu
			$(document).on('click','.poista', function(){ //Ajetaan poista-luokan napin painamisella, tässä tietotekniikkatuote poistetaan tuote_id-attribuutin mukaan
				var obj = $(this); 	
				var tuote_id = obj[0].id;
				if(confirm("Poista tuote " + tuote_id + "?")){
					$.post(backPath+"PoistaTtTuote", {tuote_id: tuote_id, sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
				        if(result==0){
				        	$("#ilmo").jsp("Tuotteen poisto epäonnistui.");
				        }else if(result==1){
				        	$("#"+tuote_id).css("background-color", "red"); //Värjätään poistetun tuotteen (tietotekniikka) rivi
				        	alert("Tuotteen "+ tuote_id +" poisto onnistui.");
							document.location="listaaTietotekniikkatuotteet.jsp";		        	
						}
				    });
				}
			});
			
			$("#uusiTietotekniikkatuote").click(function(){   // TEHTÄVÄ!
				document.location="lisaaTtTuote.jsp";
			});
			$("#logOut").click(function(){
				$.post(backPath+"SuljeIstunto", {sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result) {
					document.location="KirjauduFront?logout=1";					
				});		
			});
			
			$("#hae").click(function(){		
				haeTiedot();
			});
			
			$(document.body).on("keydown", function(event){
				  if(event.which==13){ //Enteriä painettu, ajetaan haku, toimii myös - Arttu K.
					  haeTiedot();
				  }
			}); 
			
			
		});
		
		function haeTiedot(){	
			$("#listaus tbody").empty();
			$.getJSON(backPath+"ListaaTietotekniikkatuotteet",{hakusana: $("#hakuSana").val(), sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result){  
				<%-- Lähtee viestinä Servletille, parametri nimi tulee olemaan hakusana.
				sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, --%>	<%--// Menee talteen result-parametriksi. Tuotteet JSON:ina. Result voisi olla data. --%>
		        $.each(result.tietotekniikkatuotteet, function(i, field){  
		        	var htmlStr;
		        	htmlStr+="<tr id='"+field.tuote_id+"'>";
		        	htmlStr+="<td>"+field.tuote_id_nimi+"</td>";  <%--//Annetaan riville id:ksi rekisterinumero rivin maalaamista varten --%>
		        	<%-- //htmlStr+="<td>"+field.tuote_id_nimi+"</td>";  --%>
		        	htmlStr+="<td>"+field.tuote_malli+"</td>";
		        	<%--//htmlStr+="<tr>"+field.tuote_id+"</td>";--%>
		        	htmlStr+="<td>"+field.tuote_id+"</td>";
		        	htmlStr+="<td>"+field.tuote_kuvaus+"</td>";
		        	htmlStr+="<td>"+field.tuote_julkaisuvuosi+"</td>";
		        	htmlStr+="<td>"+field.tuote_hinta+"</td>";  
		        	
		        	htmlStr+="<td><a href='muutaTtTuote.jsp?tuote_id="+field.tuote_id+"'>Muuta</a>&nbsp;";
		        	htmlStr+="<span class='poista' id='"+field.tuote_id+"'>Poista</span></td>";
		        	htmlStr+="</tr>";
		        	$("#listaus tbody").append(htmlStr);
		        });
		      	<%-->//Jos sivun kutsun yhteydessä on tullut tuote_id  --%>
		        var tuote_id=requestURLParam("tuote_id"); //Funktio löytyy scripts/main.js
		        //tuote_id = parseInt(tuote_id);
		        if(tuote_id!=undefined){
			        $('html, body').animate({ //Rullataan tuote_id:n mukaisen tuotteen luokse
			            scrollTop: $("#"+tuote_id).offset().top
			        	}, 500);
			        $("#"+tuote_id).css("background-color", "green"); //Värjätään tuotteen rivi
			        setTimeout(function(){
			        	$("#"+tuote_id).css("background-color","");  //Poistetaan rivin värjäys aikaviiveellä							
						}, 5000);
		        }
		    });
		}
			
	<%--	haeTiedot(); //Ajetaan automaattisesti sivun latauksen yhteydessä	
			$("#hakuSana").focus();
			
			//Tässä tulee käyttää document-tason kuuntelijaa, koska poista-luokan elementit on luotu jQuerylla ja suora sidonta sivulle puuttuu
			$(document).on('click','.poista', function(){ //Ajetaan poista-luokan napin painamisella
				var obj = $(this); 	
				var rekNo = obj[0].id;
				if(confirm("Poista auto " + rekNo + "?")){
					$.post(backPath+"PoistaAuto", {rekNo: rekNo, sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
				        if(result==0){
				        	$("#ilmo").jsp("Auton poisto epäonnistui.");
				        }else if(result==1){
				        	$("#"+rekNo).css("background-color", "red"); //Värjätään poistetun auton rivi
				        	alert("Auton "+ rekNo +" poisto onnistui.");
							document.location="listaa.jsp";		        	
						}
				    });
				}
			}); --%>
	
	
	</script>










</body>
</html>
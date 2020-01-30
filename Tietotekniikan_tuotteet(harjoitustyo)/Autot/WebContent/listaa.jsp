<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<title>Autojen listaus</title>
</head>
<span id="ilmo"></span>
<body>
<table id="listaus" class="table">
	<thead>
		<tr>
			<th colspan="2"><%out.print(session.getAttribute("kayttaja"));%>&nbsp;<span id="logOut">Kirjaudu ulos</span></th>
			<th colspan="3" class="oikealle"><span id="uusiAuto">Lisää uusi auto</span></th>
		</tr>
		<tr>
			<th colspan="2" class="oikealle">Hakusana:</th>
			<th colspan="3"><input type="text" id="hakuSana"><input type="button" id="hae" value="Hae"></th>
		</tr>
		<tr>
			<th>RekNo</th>
			<th>Merkki</th>
			<th>Malli</th>
			<th>Vuosi</th>			
			<th>Hallinta</th>
		</tr>
	</thead>
	<tbody>
	</tbody>
</table>
<script>
$(document).ready(function(){	
	
	haeTiedot(); //Ajetaan automaattisesti sivun latauksen yhteydessä	
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
	});
	
	$("#uusiAuto").click(function(){
		document.location="lisaaauto.jsp";
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
		  if(event.which==13){ //Enteriä painettu, ajetaan haku
			  haeTiedot();
		  }
	});
	
});

function haeTiedot(){	
	$("#listaus tbody").empty();
	$.getJSON(backPath+"ListaaAutot",{hakuSana: $("#hakuSana").val(), sessionId: "<%out.print(session.getAttribute("sessionId"));%>"}, function(result){	
        $.each(result.autot, function(i, field){  
        	var htmlStr;
        	htmlStr+="<tr id='"+field.rekNo+"'>"; //Annetaan riville id:ksi rekisterinumero rivin maalaamista varten 
        	htmlStr+="<td>"+field.rekNo+"</td>";
        	htmlStr+="<td>"+field.merkki+"</td>";
        	htmlStr+="<td>"+field.malli+"</td>";
        	htmlStr+="<td>"+field.vuosi+"</td>";        	
        	htmlStr+="<td><a href='muutaauto.jsp?rekNo="+field.rekNo+"'>Muuta</a>&nbsp;";
        	htmlStr+="<span class='poista' id='"+field.rekNo+"'>Poista</span></td>";
        	htmlStr+="</tr>";
        	$("#listaus tbody").append(htmlStr);
        });
      	//Jos sivun kutsun yhteydessä on tullut rekisterinumero  
        var rekNo=requestURLParam("rekNo"); //Funktio löytyy scripts/main.js        
        if(rekNo!=undefined){
	        $('html, body').animate({ //Rullataan rekisterinumeron mukaisen auton luokse
	            scrollTop: $("#"+rekNo).offset().top
	        	}, 500);
	        $("#"+rekNo).css("background-color", "gray"); //Värjätään auton rivi
	        setTimeout(function(){
	        	$("#"+rekNo).css("background-color","");  //Poistetaan rivin värjäys aikaviiveellä							
				}, 5000);
        }
    });
}
</script>
</body>
</html>
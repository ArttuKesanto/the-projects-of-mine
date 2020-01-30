<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lähetä viesti</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
</head>
<body>
	Nimi: <input type="text" id="nimi" > <br />
	<input type="button" id="laheta" value="Lähetä viesti">
	<div id="result"></div>
	<script>
	$(document).ready(function() { // ready-funktiolle annetaan anonyymi-funktio, ajetaaan heti.
		$("#laheta").click(function() { // jos haluat johonkin elementtiin js-funktion kiinni, näin.
			if($("#nimi").val() != "") {
				$.post("VastaanotaViesti",{nimi: $("#nimi").val()},function() {
					$("#result").html("Viesti lähetetty!");
					
				});
				
				
			} 
			
		
		
		
		
		
		}); // etsitään painonappula nimellä id="nimi"
		
		
		
		
	}); // Voi käyttää dollari-merkkiä $, alias JQuerylle
	
	</script>
</body>
</html>
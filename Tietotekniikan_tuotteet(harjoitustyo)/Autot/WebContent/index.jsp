<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png">
<link rel="shortcut icon" href="https://findicons.com/files/icons/2650/nixus/64/computer.png" type="image/png">
<title>Kirjautuminen tietotekniikkatuotteiden listaukseen</title>
</head>
<body>
<div id="loginDiv">	
	<label>Kirjautumistunnus:</label><input type="text" id="uid"><br>
	<label>Salasana:</label><input type="password" id="pwd"><br>
	<label>&nbsp;</label><input type="button" id="loginBtn" value="Kirjaudu"><br>
	<label>&nbsp;</label><span id="ilmo"></span>
</div>

<script>
$(document).ready(function(){	
	$("#loginBtn").click(function(){	
		if($("#uid").val()!="" && $("#pwd").val()!=""){
			$.post("KirjauduFront", {uid: $("#uid").val(), pwd: $("#pwd").val()}, function(result){	//Tiedot kulkevat palvelimelta GET-metodilla JSON-muodossa	
				if(result==1){
					var kohdeSivu="<%out.print(session.getAttribute("targetPage"));%>";
					if(kohdeSivu!="null"){
						document.location=kohdeSivu;
					}else{
						document.location="listaaTietotekniikkatuotteet.jsp";				
					}	
				}else{
					$("#ilmo").html("Tuntematon tunnus/salasana. Kokeile uudestaan.");				
				}		
		    });
		}
	});
});
</script>
</body>
</html>
<%@include file="header.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="scripts/main.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.15.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<form action="LisaaAuto" id="tiedot">
<table>
	<thead>
		<tr>
			<th colspan="5" class="oikealle"><span id="takaisin">Takaisin listaukseen</span></th>
		</tr>
		<tr>
			<th>RekNo</th>
			<th>Merkki</th>
			<th>Malli</th>
			<th>Vuosi</th>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="text" name="rekNo" id="rekNo"></td>
			<td><input type="text" name="merkki" id="merkki"></td>
			<td><input type="text" name="malli" id="malli"></td>
			<td><input type="text" name="vuosi" id="vuosi"></td> 
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
		document.location="listaa.jsp";
	});
		
	$("#tiedot").validate({						
		rules: {
			rekNo:  {
				required: true,
				minlength: 3				
			},	
			merkki:  {
				required: true,
				minlength: 2				
			},
			malli:  {
				required: true,
				minlength: 1
			},	
			vuosi:  {
				required: true,
				number: true,
				minlength: 4,
				maxlength: 4,
				min: 1900,
				max: new Date().getFullYear()+1 //Auto voi olla ensivuoden mallia
			}	
		},
		messages: {
			rekNo: {     
				required: "Puuttuu",
				minlength: "Liian lyhyt"			
			},
			merkki: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			malli: {
				required: "Puuttuu",
				minlength: "Liian lyhyt"
			},
			vuosi: {
				required: "Puuttuu",
				number: "Ei kelpaa",
				minlength: "Liian lyhyt",
				maxlength: "Liian pitkä",
				min: "Liian pieni",
				max: "Liian suuri"
			}
		},			
		submitHandler: function(form) {	
			lisaaTiedot();
		}		
	});   
	$("#rekNo").focus();
});
function lisaaTiedot(){
	$.post(backPath + "LisaaAuto", $("#tiedot").serialize(), function(result) {  //Tiedot kulkevat palvelimelle POST-metodilla
        if(result==0){
        	$("#ilmo").html("Auton lisääminen epäonnistui.");
        }else if(result==1){
			document.location="listaa.jsp?rekNo=" + $("#rekNo").val();
		}
    });	
}
</script>
</body>
</html>
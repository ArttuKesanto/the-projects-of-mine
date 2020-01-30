<%
//Jos kirjautumista ei ole tapahtunut lähetetään käyttäjä kirjautumissivulle - mallipohja.
if(session.getAttribute("kayttaja")==null){	
	session.setAttribute("targetPage", request.getRequestURL());
	response.sendRedirect("index.jsp");
	return;
}
//Estetään Back-napin toiminta uloskirjautumisen jälkeen - mallipohja.
response.setHeader("Cache-Control", "no-cache");
response.setHeader("Cache-Control", "no-store");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Vector"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>

<%
	String id = "none";
	String fileName = request.getParameter("filename");
	String aa = "";
	String prescaled = request.getParameter("prescaled");
	if(prescaled == null) prescaled = "1";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Coordination Viewer</title>
<script language="javascript">
	
	
</script>
</head>
<body onLoad="" topMargin="1" leftMargin="1" scroll="no">

<form name="coordinator">
<applet onLoad="" name="coordinator" code="org.ssu.ml.presentation.CoordinatorApplet"
archive="
	coordinator.jar, 
	commons-logging.jar,
	jcommon-1.0.16.jar,
	jfreechart-1.0.13.jar,
	netscape.jar" MAYSCRIPT height="100%" width="100%" >
<param name=id value=<%=id%> />
<param name=fileName value=<%=fileName%>></param>
<param name=prescaled value=<%=prescaled%>></param>

</applet>	


<script language="javascript">
function listProcessing(node_name)
{
	alert('I am a outside of applet function.');
	alert(node_name);
}
</script>
</form>
</body>
</html>
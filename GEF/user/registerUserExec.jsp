<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file = "/../env/env.jsp" %>
<%
	String id = request.getParameter("id");

	String cmd = "mkdir";
	String result = "";
	
	//Make ID folder
	result+=exeCmd(cmd+" "+ENV_DATA_PATH+id);
	if(!result.equals(""))result+="<br>";
	//Make sub folder associated with each steps.
	String[] folders = {"source", "truncate", "layout", "correlation"};
	for(int count = 0 ; count < folders.length ; count++){
		result+=exeCmd(cmd+" "+ENV_DATA_PATH+id+"/"+folders[count]);
		if(!result.equals(""))result+="<br>";
	}
	//Make Log folder
	exeCmd(cmd+" "+ENV_LOG_PATH+id);
	out.println(result);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
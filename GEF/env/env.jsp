<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%! 
	public String exeCmd(String cmd){
		Process process = null;
		BufferedReader in = null;
		BufferedReader err = null;
		String s = "";
		String result = "";
	
		try {
			result+= "cmd : "+cmd+"<br>";
			process = Runtime.getRuntime().exec(cmd);
	
			in = new BufferedReader(new InputStreamReader(process
					.getInputStream()));
			while ((s = in.readLine()) != null) {
				result+=s + "<br>";
			}
			err = new BufferedReader(new InputStreamReader(process
					.getErrorStream()));
			while (err.ready()) {
				result+=err.readLine() + "<br>";
			}
		} catch (Exception e) {
			result+="Error : " + e;
			//System.out.println(new java.util.Date() + " process.jsp " + e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (Exception sube) {
				}
			if (err != null)
				try {
					err.close();
				} catch (Exception sube) {
				}
		}
		return result;
	}

%>
<% 
	final String ENV_ROOT_PATH = "/home/mhlee/public/server/ROOT/";
	final String ENV_DATA_PATH = ENV_ROOT_PATH+"data/";
	

%>

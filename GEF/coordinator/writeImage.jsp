<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.io.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.awt.Image" %>
<%@ page import="java.awt.Toolkit" %>
<%@ page import="java.awt.image.MemoryImageSource" %>
<%@ page import="com.sun.image.codec.jpeg.*" %>
<%@ page import="javax.imageio.*"%>

<%! 
	private Image getImageFromArray(int[] pixels, int width, int height) {
		MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.createImage(mis);
	}  //  private Image getImageFromArray()

	public static int[] String2Array(String string, String delimeter){
		String[] parts = string.split(delimeter);
		int[] result = new int[parts.length];
		
		for(int count = 0 ; count < parts.length ; count++)
				result[count] = Integer.parseInt(parts[count]);
		return result;
	}
%>
<% 
	
	String realPath = request.getRealPath("/");
	String fullPath = realPath+"/coordinator/data/images/";
	BufferedImage img = null;
	String aFileName = request.getParameter("filename");
	fullPath+=aFileName;
	
	
	int quality;
	
	out.println(fullPath);
	
	try {
	    BufferedImage bi = null; // retrieve image
	    File outputfile = new File("saved.png");
	    ImageIO.write(bi, "png", outputfile);
	} catch (Exception e) {
	   
	}
	//ServletOutputStream sos = response.getOutputStream();
	//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(sos);
	//FileOutputStream fos = new FileOutputStream(aFileName);
	// ChartUtilities.writeChartAsPNG(fos, chart, 400, 400);
	/*
	JPEGImageEncoder encoder2 = JPEGCodec.createJPEGEncoder(fos);
	JPEGEncodeParam param2 = encoder2.getDefaultJPEGEncodeParam(img);
	param2.setQuality((float) quality, true);
	encoder2.encode(img, param2);
	fos.close();
	*/
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
    
<%@ page import="java.io.*" %>
<% 
    // response.setContentType() 메소드는 출력되는 페이지의 contentType을 설정함.
    response.setContentType("application/octer-stream"); 
    String filename = request.getParameter("filename");
 
    // 경로 구하기 
    ServletContext context=getServletContext();    
    String path=context.getRealPath("/images");
    // attachment를 사용하면 브라우저는 무조건 다운로드 창을 띄우고 파일명을 보여줌
    response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
 
    out.println(filename+"\n");
    out.println(path);
 
 
    /** 파일 다운로드 */
    File fp = new File(path + "/" + filename);
    int read = 0;
    byte[] b = new byte[(int)fp.length()]; // 파일 크기
 
    if (fp.isFile()) {
        out.println("success");
        BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fp));
        BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
        // 파일 읽어서 브라우저로 출력하기 
            try {
                while( (read=fin.read(b) ) != -1){
                outs.write(b, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                if (outs != null) {outs.close();}
                if (fin != null) {fin.close();}
           }
    }
 
%>
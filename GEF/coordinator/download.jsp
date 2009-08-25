<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
    
<%@ page import="java.io.*" %>
<% 
    // response.setContentType() �޼ҵ�� ��µǴ� �������� contentType�� ������.
    response.setContentType("application/octer-stream"); 
    String filename = request.getParameter("filename");
 
    // ��� ���ϱ� 
    ServletContext context=getServletContext();    
    String path=context.getRealPath("/images");
    // attachment�� ����ϸ� �������� ������ �ٿ�ε� â�� ���� ���ϸ��� ������
    response.setHeader("Content-Disposition", "attachment;filename="+filename+";");
 
    out.println(filename+"\n");
    out.println(path);
 
 
    /** ���� �ٿ�ε� */
    File fp = new File(path + "/" + filename);
    int read = 0;
    byte[] b = new byte[(int)fp.length()]; // ���� ũ��
 
    if (fp.isFile()) {
        out.println("success");
        BufferedInputStream fin = new BufferedInputStream(new FileInputStream(fp));
        BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
        // ���� �о �������� ����ϱ� 
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
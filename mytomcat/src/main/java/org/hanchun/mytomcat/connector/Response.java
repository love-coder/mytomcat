package org.hanchun.mytomcat.connector;

import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletResponse;

import org.hanchun.utils.Constants;

import javax.servlet.ServletOutputStream;

/**
 * 封装response请求
 * @author HanChun
 * @version 1.0 2016-5-14
 */
public class Response implements ServletResponse {

	private static final int BUFFER_SIZE = 1024;  //缓冲大小
	Request request;
	OutputStream output;
	PrintWriter writer;

	public Response(PrintWriter writer) {
		this.writer = writer;
	}

	public Response(OutputStream output) {
		super();
		this.output = output;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	/**
	 * 请求图片或文件等静态信息
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(Constants.WEB_RESOURCES, request.getUri());
			fis = new FileInputStream(file);//文件的输入流
		  
			int ch = fis.read(bytes, 0, BUFFER_SIZE);
		  
			while (ch!=-1) {
				output.write(bytes, 0, ch);
				ch = fis.read(bytes, 0, BUFFER_SIZE); 
			}
		}catch (FileNotFoundException e) {
			String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
		    "Content-Type: text/html\r\n" +
		    "Content-Length: 23\r\n" +
		    "\r\n" +
		    "<h1>无效路径</h1>";
		  output.write(errorMessage.getBytes()); 
		}
		finally {
			if (fis!=null)
		    fis.close();
		}
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public PrintWriter getWriter() throws IOException {
		 writer = new PrintWriter(output, true);
		 return writer;
	}

	public void setCharacterEncoding(String charset) {
		// TODO Auto-generated method stub
		
	}

	public void setContentLength(int len) {
		// TODO Auto-generated method stub
		
	}

	public void setContentType(String type) {
		// TODO Auto-generated method stub
		
	}

	public void setBufferSize(int size) {
		// TODO Auto-generated method stub
		
	}

	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	public void setLocale(Locale loc) {
		// TODO Auto-generated method stub
		
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}
  
}
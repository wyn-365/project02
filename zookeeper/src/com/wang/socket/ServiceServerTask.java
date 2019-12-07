package com.wang.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.InflaterInputStream;

public class ServiceServerTask implements Runnable{
	
	Socket socket;
	InputStream in = null;
	OutputStream out = null;
	public ServiceServerTask(Socket socket) {
		this.socket=socket;
	}
	
	//业务逻辑，和客户端进行交互
	@Override
	public void run() {
		//
		try {
			//连接中获取到与client网络通信输入输出流
			in = socket.getInputStream();	
			out = socket.getOutputStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			//读取客户端的发送的一行信息
			String param = br.readLine();
			
			GetDtataServiceImpl getDtataServiceImpl = new GetDtataServiceImpl();
			String result = getDtataServiceImpl.getData(param);
			
			//将调用结果发送给客户端
			PrintWriter pw = new PrintWriter(out);
			pw.println(result);
			pw.flush();
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			try {
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}

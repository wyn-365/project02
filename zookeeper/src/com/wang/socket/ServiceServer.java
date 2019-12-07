package com.wang.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServiceServer {
	
	public static void main(String[] args) throws Exception {
		
		//创建一个serverSocket 绑定本机的8899端口号
		ServerSocket server= new ServerSocket();
		server.bind(new InetSocketAddress("localhost",8899));
		
		//接受客户端的连接请求,是一个阻塞方法，一直等啊
		while(true) {
			
			Socket socket = server.accept();
			new Thread(new ServiceServerTask(socket)).start();
			
		}
		
	}
}

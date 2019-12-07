package com.wang.socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import com.sun.corba.se.spi.orbutil.fsm.Input;

public class ServiceClient {
	public static void main(String[] args) throws Exception {
		//向服务器发出请求连接
		Socket socket = new Socket("localhost",8899);
		
		//socket获取输出流
		InputStream in = socket.getInputStream();
		OutputStream out = socket.getOutputStream();
		Scanner input=new Scanner(System.in);
		String put = input.nextLine();
		PrintWriter pw = new PrintWriter(out);
		pw.println(put);
		pw.flush();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String result = br.readLine();
		System.out.println(result);
		
		in.close();
		out.close();
		socket.close();
	}
}

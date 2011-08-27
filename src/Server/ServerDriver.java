package Server;

import java.io.*;
import java.net.*;

public class ServerDriver {
	static final int DEFAULT_PORT = 1234;
	
	private int port = DEFAULT_PORT;
	
	ServerDriver(){
		run();
	}
	
	public void run(){
		System.out.println("SQL accepting connections on 1234");
		try{
			ServerSocket ss = new ServerSocket( port);
			while(true){
				Socket socket = ss.accept();
				new ServerHandler(socket);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerDriver server = new ServerDriver();
	}

}

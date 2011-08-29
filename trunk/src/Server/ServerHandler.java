package Server;

import java.io.*;
import java.net.*;

public class ServerHandler extends Thread{
	Socket socket;
	BufferedReader in;
	DataOutputStream out;
	static String user;
	static String password;
	
	public ServerHandler(Socket socket){
		this.socket = socket;
		
		start();
	}
	
	public void run(){
		String command;
		String database;
		
		try{
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			
			command = receive();
			
			if(command.length()>=4 && command.substring(0,4).equals("user")){
				String sub = command.substring(4);
				String [] userMessage = sub.split("!");
				user = userMessage[0];
				password = userMessage[1];
				database = userMessage[2];
				System.out.println("User:" + user + "  Password:" + password + " Database:" + database);
				//检验用户登录信息
				
				send("yes");
			}
			else if(command.length()>=8 && command.substring(0,8).equals("register")){
				String sub = command.substring(8);
				String [] userMessage = sub.split("!");
				user = userMessage[0];
				password = userMessage[1];
				System.out.println("User:" + user + "  Password:" + password);
				//将用户信息加入数据库文件中
				
				send("yes");
			}
			else{
				if( command.equalsIgnoreCase("exit") ){
					//用户管理???
					System.out.println("exit-yes");
					
					send("yes");
				}
				else{   //run query
					
					//TranslateDriver translateDriver = new TranslateDriver(commond);
					//send(translateDriver.translateDriver(user));
					send("yes");
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	//public static void main(String[] args) {
		// TODO Auto-generated method stub

	//}

	public void send(String s) throws IOException{
		String command = s + "\r\n";
		out.writeBytes(command);
		out.flush();
	}
	
	public String receive() throws IOException{
		String s = in.readLine();
		return s;
	}
}

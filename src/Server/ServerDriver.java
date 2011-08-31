package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ServerDriver extends Thread{
	static final int DEFAULT_PORT = 1024;
	
	ServerSocket ss;

	ArrayList<ServerThread> st = new ArrayList<ServerThread>();

	ServerDriver(){
		start();
	}
	public void run(){
		try {
			ss = new ServerSocket(DEFAULT_PORT);
			//System.out.println(InetAddress.getLocalHost());
			//System.out.println("sever works");
			while(true){
				Socket socket = ss.accept();		
				//System.out.println("new serverThread");	
				st.add(new ServerThread(socket));
			}	
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	public void finish(){
		
		try {
			ss.close();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Now ss.accept() is blocked");
		}
		
		for(int i=0;i<st.size();i++){
			if(st.get(i).isAlive()){
				try {
					st.get(i).send("exit");
				} catch (IOException e) {
					e.printStackTrace();
				}
				st.get(i).stop();
			}
		}
		this.stop();
	}
	
	public static void main(String[] args) throws IOException {
		new ServerDriver();
	}
	
	class ServerThread extends Thread{
		Socket s;
		String user;
		String password;
		String database;
		BufferedReader in;
		DataOutputStream out;
		
		ServerThread(Socket socket) throws IOException{
			user = "no user";
			s = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new DataOutputStream(socket.getOutputStream());
			
			start();
		}
		
		public void run(){
			while(true){
				try {
					String command = in.readLine();
					if(command!=null){
						//System.out.println(command);
						if(command.startsWith("user")){
							String sub = command.substring(4);
							String [] userMessage = sub.split("!");
							user = userMessage[0];
							password = userMessage[1];
							database = userMessage[2];
							
							//System.out.println("User:" + user + "  Password:" + password + "Database:" + database);
							//检验用户登录信息
							if(!DBInfo.DbMani.hasUser("admin"))
								DBInfo.DbMani.addUser("admin", "admin");
							if(!DBInfo.DbMani.checkUser(user, password))
							{
								send("no");
								continue;
							}
							send("yes");
						}
						else if(command.startsWith("register")){
							String sub = command.substring(8);
							String [] userMessage = sub.split("!");
							user = userMessage[0];
							password = userMessage[1];
							System.out.println("User:" + user + "  Password:" + password);
							//将用户信息加入数据库文件中
							DBInfo.DbMani.addUser(user, password);
							send("yes");
						}
						else if(command.equals("exit")){
							break;//this.stop();
						}
						else{
							//
							String result=Running.database.runing(command,this.database,this.user);
							send(result);
							//send("yes");
						}
						//System.out.println("user:"+user+"'s SQL is done");
					}			
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
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
}
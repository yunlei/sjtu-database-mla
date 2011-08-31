package Server;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ServerFrame extends JFrame{
	
	JButton b1 = new JButton("开启");
	JButton b2 = new JButton("关闭");
	
	JPanel p = new JPanel(new BorderLayout());
	JPanel pUp = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel pDown = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	
	JLabel l1 = new JLabel("Server State is:",10);
	JTextField t1 = new JTextField(5);
	
	public ServerFrame() throws IOException{
		super("DBMS Server");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 200, 220, 140);
		this.setVisible(true);
		
		Container c = this.getContentPane();
		c.add(p);
		p.add(pUp,BorderLayout.NORTH);
		p.add(pDown,BorderLayout.CENTER);
		
		t1.setEditable(false);
		t1.setText("off");
		
		b1.setEnabled(true);
		b2.setEnabled(false);
		
		pUp.add(l1);
		pUp.add(t1);
		pDown.add(b1);
		pDown.add(b2);
		
		p.updateUI();
		
		MyActionListener mal = new MyActionListener();
		
		b1.addActionListener(mal);
		b2.addActionListener(mal);
	}
	
	public static void main(String[] args) throws IOException
	{
		System.out.println(InetAddress.getLocalHost());
		ServerFrame frame = new ServerFrame();
	}
	
	class MyActionListener implements ActionListener{
		ServerDriver sd;
		MyActionListener(){
			//this.sd = sd;
		}
		public void actionPerformed(ActionEvent arg0){
			if(arg0.getActionCommand().equals("开启")){
				t1.setText("on");
				b1.setEnabled(false);
				b2.setEnabled(true);
				sd = new ServerDriver();
				System.out.println("on");
			}
			else if(arg0.getActionCommand().equals("关闭")){
				t1.setText("off");
				b2.setEnabled(false);
				b1.setEnabled(true);
				sd.finish();
				System.out.println("off");
			}
		}
	}
}



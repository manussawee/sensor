package lib;

import java.io.*;
import java.net.*;

import application.Main;
import logic.GameManager;
import logic.StartManager;
public class Provider extends SocketService {
    
    Socket connection = null;

    public Provider(){}
    public void run()
    {
    	mode = "PROVIDER: ";
    	isStop = false;
    	thread = new Thread(() -> {
	        try {
	            System.out.println("Waiting for connection");
	            connection = providerSocket.accept();
	            GameManager.setReady(true);
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                 
                Thread dataSyncthread = new Thread(() -> {
                	syncData();
                });
                dataSyncthread.start();

                receiveData();
	        }
	        catch(IOException ioException){
	            ioException.printStackTrace();
	            isStop = true;
	        }
	        
	        try {
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        Main.changeManager(new StartManager());
    	});
    	
    	thread.start();
    }   
}
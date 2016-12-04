package lib;

import java.io.*;
import java.net.*;

import application.Main;
import logic.StartManager;
public class Requester extends SocketService {
    Socket requestSocket;
    String ipAddress;
    
    public Requester(String ipAddress) {
    	this.ipAddress = ipAddress;
    }

    public void run()
    {
    	mode = "REQUESTER: ";
    	isStop = false;
    	thread = new Thread(() -> {
	        try{
	        	
	            requestSocket = new Socket(ipAddress, 2016);
	            System.out.println("Connected to localhost in port 2016");
	            out = new ObjectOutputStream(requestSocket.getOutputStream());
	            out.flush();
	            in = new ObjectInputStream(requestSocket.getInputStream());
	            
	            dataSyncthread = new Thread(() -> {
                	syncData();
                });
                dataSyncthread.start();
	            
	            receiveData();
	            
	        }
	        catch(ConnectException connectExpt) {
	        	System.out.println("server not found");
	        	reportError("SERVER NOT FOUND", "CONNECTION PROBLEMS");
	        	isStop = true;
	        	Main.changeManager(new StartManager());
	        }
	        catch(IOException ioException){
	            ioException.printStackTrace();
	            isStop = true;
	        }
	        
	        Main.changeManager(new StartManager());
    	});
    	
    	thread.start();
    }
    
}
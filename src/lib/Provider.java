package lib;

import java.io.*;
import java.net.*;

import logic.GameManager;
public class Provider extends SocketService{
    
	ServerSocket providerSocket;
    Socket connection = null;

    public Provider(){}
    public void run()
    {
    	thread = new Thread(() -> {
	        try{

	            providerSocket = new ServerSocket(2016);
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
	        }
	        finally{
	            try{
	                in.close();
	                out.close();
	                providerSocket.close();
	            }
	            catch(IOException ioException){
	                ioException.printStackTrace();
	            }
	        }
    	});
    	
    	thread.start();
    }   
}
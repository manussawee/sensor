package lib;

import java.io.*;
import java.net.*;

import logic.GameManager;
public class Provider{
    ServerSocket providerSocket;
    Socket connection = null;
    static ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Data data;

    public Provider(){}
    public void run()
    {
    	Thread thread = new Thread(() -> {
	        try{

	            providerSocket = new ServerSocket(2016);
	            System.out.println("Waiting for connection");
	            connection = providerSocket.accept();
	            GameManager.setReady(true);
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());

                while(true) {
                    try{
                        message = (String)in.readObject();
                        data = new Data(message);
                        if(data.getOption().equals("MOVE")) {
                        	GameManager.enemyMove(data.getData());
                        }
                        
            			Thread.sleep(1000/60);
                    }
                    catch (ClassNotFoundException classNot) {
                        System.err.println("data received in unknown format");
                    } 
                    catch (IOException e) {
    					e.printStackTrace();
    					System.err.println("exception error");
    				} 
                    catch (InterruptedException e) {
    					e.printStackTrace();
    					System.err.println("sleep error");
    				}
                }
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

    static void sendMessage(String msg)
    {
        try{
            out.writeObject(msg);
            out.flush();
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void sendMove(String direction) {
    	sendMessage("MOVE " + direction);
    }
    
}
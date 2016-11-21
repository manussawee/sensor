package lib;

import java.io.*;
import java.net.*;

import logic.GameManager;
public class Requester{
    Socket requestSocket;
    static ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Data data;
    String ipAddress;
    
    public Requester(String ipAddress) {
    	this.ipAddress = ipAddress;
    }

    public void run()
    {
    	Thread thread = new Thread(() -> {
	        try{
	        	
	            requestSocket = new Socket(ipAddress, 2004);
	            System.out.println("Connected to localhost in port 2004");
	            out = new ObjectOutputStream(requestSocket.getOutputStream());
	            out.flush();
	            in = new ObjectInputStream(requestSocket.getInputStream());
	            
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
	        catch(UnknownHostException unknownHost){
	            System.err.println("You are trying to connect to an unknown host!");
	        }
	        catch(IOException ioException){
	            ioException.printStackTrace();
	        }
	        finally{
	            try{
	                in.close();
	                out.close();
	                requestSocket.close();
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
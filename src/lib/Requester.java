package lib;

import java.io.*;
import java.net.*;

import application.Main;
import logic.GameManager;
import logic.StartManager;
public class Requester extends SocketService{
    Socket requestSocket;
    String message;
    Data data;
    String ipAddress;
    
    public Requester(String ipAddress) {
    	this.ipAddress = ipAddress;
    }

    public void run()
    {
    	thread = new Thread(() -> {
	        try{
	        	
	            requestSocket = new Socket(ipAddress, 2016);
	            System.out.println("Connected to localhost in port 2016");
	            out = new ObjectOutputStream(requestSocket.getOutputStream());
	            out.flush();
	            in = new ObjectInputStream(requestSocket.getInputStream());
	            
	            Thread dataSyncthread = new Thread(() -> {
                	while(true) {
                    	try {
                    		System.out.println("DATASYNC");
                        	sendHero(GameManager.heroes[0].getX(), GameManager.heroes[0].getY(), 1);
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                	}
                });
                dataSyncthread.start();
	            
	            while(true) {
	                try{
	                	message = (String)in.readObject();
	                    data = new Data(message);
	                    dataController(data); 
	                }
	                catch (EOFException e) {
	                	System.err.println("player lost connection");
	                	break;
	                }
	                catch (ClassNotFoundException classNot) {
	                    System.err.println("data received in unknown format");
	                    break;
	                } 
	                catch (IOException e) {
						e.printStackTrace();
						System.err.println("exception error");
						break;
					} 
	            }
	            
	        }
	        catch(ConnectException connectExpt) {
	        	System.out.println("server not found");
	        	Main.changeManager(new StartManager());
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
    
}
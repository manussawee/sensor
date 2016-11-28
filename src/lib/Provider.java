package lib;

import java.io.*;
import java.net.*;

import logic.GameManager;
public class Provider extends SocketService{
    
	ServerSocket providerSocket;
    Socket connection = null;
    String message;
    Data data;

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
                	while(true) {
                    	try {
                    		System.out.println("DATASYNC");
                    		String strMap = "";
                        	for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
                        		for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
                        			strMap += GameManager.map.getMapAt(j, i) + "/" + GameManager.map.getLastUpdateAt(j, i) + ",";
                        		}
                        	}
                        	strMap += "END";
                        	sendMap(strMap);
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
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
	            //1. creating a server socket
	            providerSocket = new ServerSocket(2004);
	            //2. Wait for connection
	            System.out.println("Waiting for connection");
	            connection = providerSocket.accept();
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                //3. get Input and Output streams
                out = new ObjectOutputStream(connection.getOutputStream());
                out.flush();
                in = new ObjectInputStream(connection.getInputStream());
                // sendMessage("Connection successful");
                //4. The two parts communicate via the input and output streams

                do{
                    try{
                        message = (String)in.readObject();
                        data = new Data(message);
//                        System.out.println("CLIENT TO SERVER " + message);
                        if(data.getOption().equals("MOVE")) {
                        	GameManager.enemyMove(data.getData());
                        }
                    }
                    catch(ClassNotFoundException classnot){
                        System.err.println("Data received in unknown format");
                    }
                }while(true);
	        }
	        catch(IOException ioException){
	            ioException.printStackTrace();
	        }
	        finally{
	            //4: Closing connection
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
//            System.out.println("server>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void sendMove(String direction) {
    	sendMessage("MOVE " + direction);
    }
    
}
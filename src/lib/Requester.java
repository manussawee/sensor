package lib;

import java.io.*;
import java.net.*;
import java.util.Random;

import logic.GameManager;
public class Requester{
    Socket requestSocket;
    static ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    Data data;
    String channel;
    static String enemyChannel;

    public void run()
    {
    	Thread thread = new Thread(() -> {
        try{
            //1. creating a socket to connect to the server
            requestSocket = new Socket("localhost", 2004);
            System.out.println("Connected to localhost in port 2004");
            //2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            //3: Communicating with the server
            
            Random rand = new Random();
            channel = "ch_" + rand.nextInt(1000000);
            
            sendMessage(channel + " INIT null");
            
            while(true) {
                try{
                    message = (String) in.readObject();
                    data = new Data(message);
                    System.out.println(message);
        			if(data.getOption().equals("INIT")) {
        				if(data.getChannel() == channel) {
        					System.out.println("CREATE MY HERO");
        					GameManager.createMyHero(data.getData());
        				}
        				else {
        					System.out.println("CREATE ENEMY HERO");
        					GameManager.createEnemyHero(data.getData());
        				}
        			}
        			else if(data.getOption().equals("READY") && data.getChannel().equals(channel)) {
        				GameManager.setReady(Boolean.parseBoolean(data.getData()));
        			}
        			else if(data.getOption().equals("MOVE") && !data.getChannel().equals(channel)){
        				enemyChannel = data.getChannel();
        				GameManager.enemyMove(data.getData());
        			}
        			
        			Thread.sleep(1000/60);
                    
                }
                catch(ClassNotFoundException classNot){
                    System.err.println("data received in unknown format");
                } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println("exception error");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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
            //4: Closing connection
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
            System.out.println("client>" + msg);
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }
    
    public static void sendMove(String direction) {
    	sendMessage(enemyChannel + " MOVE " + direction);
    }
    
}
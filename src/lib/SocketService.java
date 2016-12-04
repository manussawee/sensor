package lib;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.ServerSocket;
import java.net.SocketException;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logic.GameManager;

public abstract class SocketService {
	
	protected static ServerSocket providerSocket;
	protected static ObjectOutputStream out;
	protected ObjectInputStream in;
	protected static Thread thread;
	protected static boolean isStop = true;
	protected String message;
    protected Data data;
    protected static String mode;
    
	abstract public void run();
	
	static {
		try {
			providerSocket = new ServerSocket(2016);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		}
		catch(SocketException e) {
			System.err.print(mode);
			System.err.println("CONNECTION ERROR");
			isStop = true;
		}
		catch(IOException e) {
			System.err.print(mode);
			System.err.println("IO ERROR");
			isStop = true;
		}
	}
	
	public static boolean isEnable() {
		return !isStop;
	}
	
	public static void stop() {
		thread.interrupt();
		
	}
	    
	
	public void sendMove(String direction) {
    	sendMessage("MOVE " + direction);
    }
	
	public void sendSkill(int index) {
		sendMessage("SKILL " + index);
	}
	
	public void sendMap(String strMap) {
		sendMessage("MAP " + strMap);
	}
	
	public void sendHero(int x, int y, int id) {
		sendMessage("HERO " + x + "," + y + "," + id);
	}
	
	public void dataController(Data data) throws NumberFormatException {
        if(data.getOption().equals("MOVE")) {
        	GameManager.enemyMove(data.getData());
        }
        else if(data.getOption().equals("SKILL")) {
        	GameManager.enemySkill(Integer.parseInt(data.getData()));
        }
        else if(data.getOption().equals("MAP")) {
        	syncMap(data.getData());
        }
        else if(data.getOption().equals("HERO")) {
        	syncHero(data.getData());
        }
	}
	
	private void syncHero(String data) {
		String[] dt = data.split(",");
    	int x = Integer.parseInt(dt[0]);
    	int y = Integer.parseInt(dt[1]);
    	int index = Integer.parseInt(dt[2])%2;
    	GameManager.heroes[index].setX(x);
        GameManager.heroes[index].setY(y);
	}
	
	private void syncMap(String data) {
		String[] dt = data.split(",");
    	
    	for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
    		for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
    			
    			int mapType = Integer.parseInt(dt[j-1 + (i-1) * ConfigurableOption.mapHeight]);
    			int currentType = GameManager.map.getMapAt(j, i);
    			
    			if(mapType == 1) mapType = 2;
    			else if(mapType == 2) mapType = 1;
    			
    			if(mapType != currentType) {
    				
    				if(currentType == 0) {
    					if(mapType == -1) mapType = 0;
    					else if(mapType == 1) mapType = 0;
    					else if(mapType == 2) mapType = 2;
    				}
    				else if(currentType == -1) {
    					if(mapType == 0) mapType = 0;
    					else if(mapType == 1) mapType = 1;
    					else if(mapType == 2) mapType = -1;
    				}
    				else if(currentType == 1) {
    					if(mapType == 0) mapType = 1;
    					else if(mapType == -1) mapType = -1;
    					else if(mapType == 2) mapType = -1;
    				}
    				else if(currentType == 2) {
    					if(mapType == 0) mapType = 0;
    					else if(mapType == -1) mapType = 2;
    					else if(mapType == 1) mapType = 0;
    				}
    				
    				GameManager.map.setMapAt(j, i, mapType);
    			}
    			
    		}
    	}
	}
	
	protected void syncData() {
		while(true && !isStop) {
        	try {
        		String strMap = "";
            	for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
            		for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
            			strMap += GameManager.map.getMapAt(j, i) + ",";
            		}
            	}
            	strMap += "END";
            	sendMap(strMap);
            	sendHero(GameManager.heroes[0].getX(), GameManager.heroes[0].getY(), 1);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.print(mode);
				e.printStackTrace();
			}
    	}
	}
	
	protected void receiveData() {
		while(true && !isStop) {
            try{
            	message = (String)in.readObject();
            	data = new Data(message);
                dataController(data); 
            }
            catch (EOFException e) {
            	System.err.print(mode);
            	System.err.println("ENEMY PLAYER LOST CONNECTION");
            	reportError("ENEMY PLAYER LOST CONNECTION", "CONNECTION PROBLEMS");
            	isStop = true;
            	break;
            }
            catch (StreamCorruptedException e) {
            	System.err.print(mode);
            	System.err.println("CONNECTION ERROR");
            	reportError("CONNECTION ERROR", "CONNECTION PROBLEMS");
            	isStop = true;
            	break;
            }
            catch (Exception e) {
            	System.err.print(mode);
            	e.printStackTrace();
            	reportError("CONNECTION ERROR", "CONNECTION PROBLEMS");
            	isStop = true;
            	break;
            }
        }
	}
	
	protected void reportError(String message, String title) {
		Platform.runLater(() -> {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle(title);
        	alert.setContentText(message);
        	alert.setHeaderText(null);
        	alert.showAndWait();
    	});
	}
}

package lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

import logic.GameManager;

public abstract class SocketService {
	
	protected static ObjectOutputStream out;
	protected ObjectInputStream in;
	protected static Thread thread;
	protected static boolean isStop = false;
    
	abstract public void run();
	
	static void sendMessage(String msg) {
		try {
			out.writeObject(msg);
			out.flush();
		}
		catch(SocketException e) {
			System.err.println("CONNECTION ERROR");
			isStop = true;
		}
		catch(IOException e) {
			System.err.println("IO ERROR");
			isStop = true;
		}
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
        	String[] dt = data.getData().split(",");
        	for(int i = 1; i <= ConfigurableOption.mapHeight; i++) {
        		for(int j = 1; j <= ConfigurableOption.mapWidth; j++) {
        			String[] dtAt = dt[j-1 + (i-1) * ConfigurableOption.mapHeight].split("/");
        			int mapType = Integer.parseInt(dtAt[0]);
        			int lastUpdate = Integer.parseInt(dtAt[1]);
        			
        			if(mapType == 1) mapType = 2;
        			else if(mapType == 2) mapType = 1;
        			
        			if(lastUpdate > GameManager.map.getLastUpdateAt(j, i)) {
        				GameManager.map.setMapAt(j, i, mapType);
        				GameManager.map.setLastUpdateAt(j, i, lastUpdate);
        			}
        			else if(lastUpdate == GameManager.map.getLastUpdateAt(j, i)) {
        				if(mapType == -1 || mapType + GameManager.map.getMapAt(j, i) == 3) {
        					GameManager.map.setMapAt(j, i, -1);
            				GameManager.map.setLastUpdateAt(j, i, lastUpdate);
        				}
        				else if(mapType + GameManager.map.getMapAt(j, i) == 0) {
        					GameManager.map.setMapAt(j, i, mapType);
            				GameManager.map.setLastUpdateAt(j, i, lastUpdate);
        				}
        			}
        		}
        	}
        }
        else if(data.getOption().equals("HERO")) {
        	String[] dt = data.getData().split(",");
        	int x = Integer.parseInt(dt[0]);
        	int y = Integer.parseInt(dt[1]);
        	int index = Integer.parseInt(dt[2])%2;
        	GameManager.heroes[index].setX(x);
            GameManager.heroes[index].setY(y);
        }
	}
}

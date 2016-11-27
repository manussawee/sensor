package lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.GameManager;

public abstract class SocketService {
	
	protected static ObjectOutputStream out;
	protected ObjectInputStream in;
	protected static Thread thread;
    
	abstract public void run();
	
	static void sendMessage(String msg)
	{
		try {
			out.writeObject(msg);
			out.flush();
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
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
	
	public void dataController(Data data) {
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
        			int mapType = Integer.parseInt(dt[j-1 + (i-1) * ConfigurableOption.mapHeight]);
        			if(mapType == 1) mapType = 2;
        			else if(mapType == 2) mapType = 1;
        			GameManager.map.setMapAt(j, i, mapType);
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

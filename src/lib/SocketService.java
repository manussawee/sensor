package lib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logic.GameManager;

public abstract class SocketService {
	
	static ObjectOutputStream out;
    ObjectInputStream in;
    
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
	    
	
	public void sendMove(String direction) {
    	sendMessage("MOVE " + direction);
    }
	
	public void sendSkill(int index) {
		sendMessage("SKILL " + index);
	}
	
	public void dataController(Data data) {
        if(data.getOption().equals("MOVE")) {
        	GameManager.enemyMove(data.getData());
        }
        else if(data.getOption().equals("SKILL")) {
        	GameManager.enemySkill(Integer.parseInt(data.getData()));
        }
	}
}

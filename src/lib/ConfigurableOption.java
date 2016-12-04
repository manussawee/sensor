package lib;

import javafx.scene.input.KeyCode;

public class ConfigurableOption {
	
	public static int screenWidth = 800;
	public static int screenHeight = 600;
	public static int mapWidth = 20;
	public static int mapHeight = 20;
	public static KeyCode up = KeyCode.UP;
	public static KeyCode down = KeyCode.DOWN;
	public static KeyCode left = KeyCode.LEFT;
	public static KeyCode right = KeyCode.RIGHT;
	public static KeyCode firstSkill = KeyCode.Z;
	public static KeyCode secondSkill = KeyCode.X;
	public static KeyCode ultimateSkill = KeyCode.C;
	public static int matchTime = 180;
	
	public static KeyCode getKey(String keyName) {
		if(keyName == "UP") return up;
		else if(keyName == "DOWN") return down;
		else if(keyName == "LEFT") return left;
		else if(keyName == "RIGHT") return right;
		else if(keyName == "1st SKILL") return firstSkill;
		else if(keyName == "2nd SKILL") return secondSkill;
		else if(keyName == "ULTIMATE SKILL") return ultimateSkill;
		return null;
	}
	
	public static void setKey(String keyName, KeyCode keyCode) {
		if(keyName == "UP") up = keyCode;
		else if(keyName == "DOWN") down = keyCode;
		else if(keyName == "LEFT") left = keyCode;
		else if(keyName == "RIGHT") right = keyCode;
		else if(keyName == "1st SKILL") firstSkill = keyCode;
		else if(keyName == "2nd SKILL") secondSkill = keyCode;
		else if(keyName == "ULTIMATE SKILL") ultimateSkill = keyCode;
	}
	
}

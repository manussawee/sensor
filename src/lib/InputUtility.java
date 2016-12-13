// receiving input class

package lib;

import java.util.ArrayList;
import javafx.scene.input.KeyCode;

public class InputUtility {
	
	private static int mouseX = 0;
	private static int mouseY = 0;
	private static boolean mouseLeftLastDown = false;
	private static boolean mouseLeftDown = false;
	private static boolean isMouseOnScreen = false;
	
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();
	private static ArrayList<KeyCode> keyTriggered = new ArrayList<>();
	
	public static boolean getKeyPressed(KeyCode keycode) {
		return keyPressed.contains(keycode);
	}

	public static void setKeyPressed(KeyCode keycode, boolean pressed) {
		if(pressed && !getKeyPressed(keycode)) {
			keyPressed.add(keycode);
			keyTriggered.add(keycode);
		}
		else if(!pressed && getKeyPressed(keycode)) keyPressed.remove(keycode);
	}

	public static boolean getKeyTriggered(KeyCode keycode) {
		return keyTriggered.contains(keycode);
	}

	public static void setKeyTriggered(KeyCode keycode, boolean pressed) {
		if(pressed && getKeyTriggered(keycode)) keyTriggered.remove(keycode);
	}
	public static boolean isMouseOnScreen() {
		return isMouseOnScreen;
	}
	public static boolean isMouseLeftLastDown() {
		return mouseLeftLastDown;
	}
	public static void setMouseLeftLastDown(boolean mouseLeftLastDown) {
		InputUtility.mouseLeftLastDown = mouseLeftLastDown;
	}
	public static boolean isMouseLeftDown() {
		return mouseLeftDown;
	}
	public static void setMouseLeftDown(boolean mouseLeftDown) {
		InputUtility.mouseLeftDown = mouseLeftDown;
	}
	public static void setMouseOnScreen(boolean isMouseOnScreen) {
		InputUtility.isMouseOnScreen = isMouseOnScreen;
	}
	public static int getMouseX() {
		return mouseX;
	}
	public static void setMouseX(int mouseX) {
		InputUtility.mouseX = mouseX;
	}
	public static int getMouseY() {
		return mouseY;
	}
	public static void setMouseY(int mouseY) {
		InputUtility.mouseY = mouseY;
	}

	public static void clearKeyTriggered() {
		// TODO Auto-generated method stub
		keyTriggered.clear();
	}
}

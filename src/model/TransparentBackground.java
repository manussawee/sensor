// background that make the text is more obvious

package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lib.ConfigurableOption;
import lib.IRenderableObject;

public class TransparentBackground implements IRenderableObject {
	
	private Color color;
//	private double alpha;
	private int z;
	
	public TransparentBackground(Color color, int z) {
		// TODO Auto-generated constructor stub
		this.color = color;
		this.z = z;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(color);
		gc.fillRect(0, 0, ConfigurableOption.screenWidth, ConfigurableOption.screenHeight);
	}

}

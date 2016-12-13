// template of displayed object

package lib;

import javafx.scene.canvas.GraphicsContext;

public interface IRenderableObject {
	public boolean isVisible();
	public int getZ();
	public void render(GraphicsContext gc);
}

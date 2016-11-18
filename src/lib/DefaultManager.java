package lib;

public abstract class DefaultManager {
	
	public abstract void update();
	
	public Pointable getTopMostTargetAt(int x, int y) {
		Pointable obj = null;
		for(IRenderableObject target: IRenderableHolder.getInstance().getEntities()) {
			if(target instanceof Pointable) {
				if(((Pointable) target).contains(x, y)) {
					obj = (Pointable) target;
				}
			}
		}
		return obj;
	}
}

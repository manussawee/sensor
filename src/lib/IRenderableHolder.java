package lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class IRenderableHolder {
	private static final IRenderableHolder instance = new IRenderableHolder();
	
	private List<IRenderableObject> entities;
	private Comparator<IRenderableObject> comparator;
	
	private static Image[] spaceship;
	public static Image[][] spaceshipFrames;
	private static Image[] explosion;
	public static Image[][] explosionFrames;
	public static Image[] planet;
	public static Image backgroundImage;
	public static Image[] backgroundFrames;
	// sound
	public static AudioClip bg;
	
	public static String mainFontName;
	
	public IRenderableHolder() {
		entities = new ArrayList<IRenderableObject>();
		comparator = (IRenderableObject o1, IRenderableObject o2) -> {
			if (o1.getZ() > o2.getZ())
				return 1;
			return -1;
		};
	}
	
	static{
		loadResource();
	}
	
	public static IRenderableHolder getInstance() {
		return instance;
	}
	
	private static void loadResource() {
		// TODO Auto-generated method
		backgroundImage = new Image(ClassLoader.getSystemResource("img/background.png").toString());
		backgroundFrames = new Image[6];
		for(int i = 0; i < 6; i++) {
			backgroundFrames[i] = new WritableImage(backgroundImage.getPixelReader(), (int) (backgroundImage.getWidth() * i / 6), 0, (int) (backgroundImage.getWidth() / 6), (int) backgroundImage.getHeight());
		}
		
		planet = new Image[3];
		planet[0] = new Image(ClassLoader.getSystemResource("img/planet_gray.png").toString());
		planet[1] = new Image(ClassLoader.getSystemResource("img/planet_blue.png").toString());
		planet[2] = new Image(ClassLoader.getSystemResource("img/planet_red.png").toString());
		
		explosion = new Image[2];
		explosion[0] = new Image(ClassLoader.getSystemResource("img/explosion_blue.png").toString());
		explosion[1] = new Image(ClassLoader.getSystemResource("img/explosion_red.png").toString());
		explosionFrames = new WritableImage[2][6];
		for(int i = 0; i < 6; i++) {
			explosionFrames[0][i] = new WritableImage(explosion[0].getPixelReader(), 350 * i, 0, 350, 350);
			explosionFrames[1][i] = new WritableImage(explosion[1].getPixelReader(), 350 * i, 0, 350, 350);
		}
		
		spaceship = new Image[2];
		spaceship[0] = new Image(ClassLoader.getSystemResource("img/spaceship_blue.png").toString());
		spaceship[1] = new Image(ClassLoader.getSystemResource("img/spaceship_red.png").toString());
		spaceshipFrames = new WritableImage[2][4];
		for(int i = 0; i < 4; i++) {
			spaceshipFrames[0][i] = new WritableImage(spaceship[0].getPixelReader(), i * 50, 0, 50, 50);
			spaceshipFrames[1][i] = new WritableImage(spaceship[1].getPixelReader(), i * 50, 0, 50, 50);
		}
		
		// load sound
		bg = new AudioClip(ClassLoader.getSystemResource("sfx/sound-bg.wav").toString());
		IRenderableHolder.bg.setVolume(0.15);
		IRenderableHolder.bg.setCycleCount((int) Double.POSITIVE_INFINITY);
		
		mainFontName = ClassLoader.getSystemResource("font/spaceranger.ttf").toString();
		
	}

	public void addAndSort(IRenderableObject entity) {
		add(entity);
		sort();
	}
	
	public void add(IRenderableObject entity) {
		entities.add(entity);
		sort();
	}
	
	public void sort(){
		Collections.sort(entities, comparator);
	}

	public List<IRenderableObject> getEntities() {
		return entities;
	}
	
	public static Color randomColor() {
		Random rand = new Random();
		double r = rand.nextDouble();
		double g = rand.nextDouble();
		double b = rand.nextDouble();
		return Color.color(r, g, b);
	}
}

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
	public static AudioClip startSound;
	public static AudioClip gameSound;
	public static AudioClip mouseOverSound;
	public static AudioClip mouseClickSound;
	public static AudioClip bombSound;
	public static AudioClip dashSound;
	public static AudioClip ultimateSoud;
	
	public static String mainFontName;
	public static Font[] mainFont;
	
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
		startSound = new AudioClip(ClassLoader.getSystemResource("sfx/start_sound.wav").toString());
		startSound.setVolume(0.2);
		startSound.setCycleCount((int) Double.POSITIVE_INFINITY);
		
		gameSound = new AudioClip(ClassLoader.getSystemResource("sfx/game_sound.wav").toString());
		gameSound.setVolume(0.5);
		gameSound.setCycleCount((int) Double.POSITIVE_INFINITY);
		
		mouseOverSound = new AudioClip(ClassLoader.getSystemResource("sfx/mouseover_sound.wav").toString());
		mouseOverSound.setVolume(0.2);
		
		mouseClickSound = new AudioClip(ClassLoader.getSystemResource("sfx/mouseclick_sound.wav").toString());
		mouseClickSound.setVolume(0.2);
		
		bombSound = new AudioClip(ClassLoader.getSystemResource("sfx/bomb_sound.wav").toString());
		bombSound.setVolume(0.5);
		
		dashSound = new AudioClip(ClassLoader.getSystemResource("sfx/dash_sound.wav").toString());
		dashSound.setVolume(0.5);
		
		ultimateSoud = new AudioClip(ClassLoader.getSystemResource("sfx/ultimate_sound.wav").toString());
		ultimateSoud.setVolume(0.5);
		
		mainFontName = ClassLoader.getSystemResource("font/spaceranger.ttf").toString();
		mainFont = new Font[200];
		
		for(int i = 0; i < 200; i++) {
			mainFont[i] = Font.loadFont(mainFontName, i);
		}
		
	}
	
	public static void stopAllSounds() {
		startSound.stop();
		gameSound.stop();
		mouseOverSound.stop();
		mouseClickSound.stop();
		bombSound.stop();
		dashSound.stop();
		ultimateSoud.stop();
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

package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;


public class DisplayManager {
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 120;
	
	private static long lastFrameTime;
	private static float delta;
	
	
	public static void createDisplay()	{
		
		
		ContextAttribs attribs = new ContextAttribs(3,2);
		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat());
			Display.setTitle("First Display");
		
		
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		//lastFrameTIme
	
	}
	
	public static void updateDisplay()	{
		
		Display.sync(FPS_CAP);
		Display.update();
		long currentTime = getCurrentTime();
		delta = (currentTime -lastFrameTime)/1000;
		
	}
	
	private static float getTimeDelat(){
		return delta;
	}
	
	public static void closeDisplay()	{
		
		Display.destroy();
		
	}
	
	//Method for calculationg the current Time
	//was included to stabelize movement update time for player Object
	private static long getCurrentTime()	{
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}

}

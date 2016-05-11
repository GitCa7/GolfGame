package engineTester;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.GolfBall;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class FlatSurfaceTest {
	
	//For rendering and displaying the Scene
	MasterRenderer renderer;
	
	//For loading Object Data
	Loader loader;
	
	//The Scene
	ArrayList<Entity> entities;
	ArrayList<Entity> surrondings;
	ArrayList<Terrain> terrains;
	Camera cam;
	Light light;
	
	
	
	public FlatSurfaceTest()	{
		DisplayManager.createDisplay();
		entities = new ArrayList<Entity>();
		surrondings = new ArrayList<Entity>();
		terrains = new ArrayList<Terrain>();
		cam = new Camera();
		renderer = new MasterRenderer();
		loader = new Loader();
		
		createSurrondings();
		setUpTerrain();
		setUpScene();
		setUpEntities();
		startGame();
	}
	
	public void createSurrondings()	{
		RawModel grassModel = OBJLoader.loadObjModel("grassModel", loader);
        ModelTexture grassModelText = new ModelTexture(loader.loadTexture("grassTexture"));
        grassModelText.setHasTranspercy(true);
        grassModelText.setUseFakeLighting(true);
        
        RawModel fernModel = OBJLoader.loadObjModel("fern", loader);
        ModelTexture fernModelText = new ModelTexture(loader.loadTexture("fern"));
        fernModelText.setHasTranspercy(true);
        fernModelText.setUseFakeLighting(true);
        
        TexturedModel grassTextModel = new TexturedModel(grassModel, grassModelText);
        TexturedModel fernTextModel = new TexturedModel(fernModel, fernModelText);
        
        Random ran = new Random();
        for(int i = 0; i< 100; i++)	{
        	
        	surrondings.add(new Entity(grassTextModel, new Vector3f(ran.nextFloat() * 800 - 400, 3, ran.nextFloat() * -600), 180, 0, 0, 3));
        	surrondings.add(new Entity(fernTextModel, new Vector3f(ran.nextFloat() * 800 - 400, 0, ran.nextFloat() * -600), 0, 0, 0, 3));
        }
	}
	
	public void setUpEntities()	{
		Entity golfBall = new GolfBall(new Vector3f(0,20,-430), 5);
		entities.add(golfBall);
		
	}
	
   
    public void setUpTerrain(){
    	Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass_surf")));
        Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass_surf")));
        terrains.add(terrain);
        terrains.add(terrain2);
    }
    
   public void setUpScene()	{
	   cam.setPosition(new Vector3f(0,20,-392));
	   light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
   }
    
   public void startGame()	{
	   while(!Display.isCloseRequested()){
           cam.move();
           
           
           for(Entity plant:surrondings)	{
        	   renderer.processEntity(plant);
           }
           
           for(Entity entity:entities)	{
        	   renderer.processEntity(entity);
           }
           
           for(Terrain terrain:terrains)	{
        	   renderer.processTerrain(terrain);
           }
           
           renderer.render(light, cam);
           DisplayManager.updateDisplay();
       }

       renderer.cleanUp();
       loader.cleanUp();
       DisplayManager.closeDisplay();
   }
    
   
   
   public static void main(String[] args)	{
	   FlatSurfaceTest test = new FlatSurfaceTest();
   }

}

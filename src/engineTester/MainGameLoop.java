package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import Shaders.StaticShader;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import renderEngine.EntityRenderer;
import textures.ModelTexture;

public class MainGameLoop {

	 public static void main(String[] args) {
		 
	        DisplayManager.createDisplay();
	        Loader loader = new Loader();
	         
	        
	        RawModel model = OBJLoader.loadObjModel("dragon", loader);
	         
	        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("stallTexture")));
	         
	        List<Entity> entities = new ArrayList<Entity>();
	        
	        Entity dragon = new Entity(staticModel, new Vector3f(30,0,-80),0,0,0,3);
	        dragon.setRotY(-40);
	        entities.add(dragon);
	        
	        
	         
	        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
	         
	        Terrain terrain = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass_surf")));
	        Terrain terrain2 = new Terrain(1,0,loader,new ModelTexture(loader.loadTexture("grass_surf")));
	         
	        Camera camera = new Camera(new Vector3f(0,20,0));
	        
	        MasterRenderer renderer = new MasterRenderer();
	         
	        while(!Display.isCloseRequested()){
	            camera.move();
	             
	            renderer.processTerrain(terrain);
	            renderer.processTerrain(terrain2);
	            
	            for(Entity entity:entities){
	                renderer.processEntity(entity);
	                
	            }
	            
	            renderer.render(light, camera);
	            DisplayManager.updateDisplay();
	        }
	 
	        renderer.cleanUp();
	        loader.cleanUp();
	        DisplayManager.closeDisplay();
	 
	    }
	
}

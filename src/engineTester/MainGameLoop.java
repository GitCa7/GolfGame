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
import renderEngine.MasterRendererHM;
import renderEngine.OBJLoader;
import terrains.Terrain;
import terrains.Terrain2;
import renderEngine.EntityRenderer;
import textures.ModelTexture;
import toolbox.MousePicker;

public class MainGameLoop {

	 public static void main(String[] args) {
		 
	        DisplayManager.createDisplay();
	        Loader loader = new Loader();
	        
	        
	        Camera camera = new Camera(new Vector3f(0,20,-100));
	        
	        MasterRendererHM renderer = new MasterRendererHM(loader);
	        
	        //MousePicker mousePick = new MousePicker(camera, renderer.getProjectionMatrix());
	        
	        RawModel model = OBJLoader.loadObjModel("cube", loader);
	         
	        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("grassNew")));
	         
	        List<Entity> entities = new ArrayList<Entity>();
	        
	        Entity dragon = new Entity(staticModel, new Vector3f(30,0,-200),0,0,0,200);
	        dragon.setRotY(-40);
	        entities.add(dragon);
	        
	        
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
	        
	        
	        
	        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
	         
	        Terrain2 terrain = new Terrain2(0,0,loader,new ModelTexture(loader.loadTexture("grassNew")), "heightmap");
	        Terrain2 terrain2 = new Terrain2(1,0,loader,new ModelTexture(loader.loadTexture("grassNew")), "heightmap");
	        Terrain2 terrain3 = new Terrain2(3,0,loader,new ModelTexture(loader.loadTexture("grassNew")), "heightmap"); 
	        
	        
	        Random ran = new Random();
	        float x,z;
	        for(int i = 0; i< 100; i++)	{
	        	x = ran.nextFloat() * 800 - 400;
	        	z = ran.nextFloat() * -1000;
	        	entities.add(new Entity(grassTextModel, new Vector3f(x, terrain.getHeightSimple(x, z), z), 180, 0, 0, 3));
	        	
	        	x = ran.nextFloat() * 800 - 400;
	        	z = ran.nextFloat() * -1000;
	        	entities.add(new Entity(fernTextModel, new Vector3f(x, terrain.getHeightSimple(x, z), z), 0, 0, 0, 3));
	        }
	        
	        
	        while(!Display.isCloseRequested()){
	            camera.move();
	            //mousePick.update();
	            //System.out.println(mousePick.getCurrentRay().x + " | " + mousePick.getCurrentRay().y + " | " + mousePick.getCurrentRay().z);
	            //System.out.println(mousePick.getCurrentRay());
	           
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

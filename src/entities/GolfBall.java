package entities;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class GolfBall extends Entity {
	
	private static final Loader loader = new Loader();
	private static final RawModel model = OBJLoader.loadObjModel("golfBallblend", loader);
	private static final TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("golfBallTexNew")));
	
	public GolfBall(Vector3f position, float scale)	{
		super(staticModel, position, 0, 0, 0, scale);
	}

}

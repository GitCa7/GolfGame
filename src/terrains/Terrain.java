package terrains;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Terrain {
    
    private static final float SIZE = 800;
    private static final int VERTEX_COUNT = 128;
     
    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;
    
    private float[] vertices;
    private float[] normals;
    float[] textureCoords;
    int[] indices;
    Loader loader;
     
    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture){
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.loader = loader;
        model = generateTerrain(loader);
    }
     
     
     
    public float getX() {
        return x;
    }
 
 
 
    public float getZ() {
        return z;
    }
 
 
 
    public RawModel getModel() {
        return model;
    }
 
 
 
    public ModelTexture getTexture() {
        return texture;
    }
    
    public float getHeightSimple(float x, float z)	{
    	
    	for(int i = 0; i < vertices.length; i+= 3)	{
    		if(vertices[i] == x && vertices[i+2] == z){
    			return vertices[i+1];
    		}
    	}
    	return 0;
    	
    }
 
    /*
    public void changeHeight(float xStart, float xLim, float zStart, float zLim, float amount)	{
    	int number = 0;
    	for(int i = 0; i < vertices.length; i+=3)	{
    		if(-vertices[i] > xStart && -vertices[i] > xLim && -vertices[i+2] > zStart && -vertices[i+2] > zLim)	{
    			if(vertices[i+1] < 30)
    				vertices[i+1] += amount;
    			
    		}
    	}
    	
    	model = loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
 	*/
    private RawModel generateTerrain(Loader loader){
        int count = VERTEX_COUNT * VERTEX_COUNT;
        vertices = new float[count * 3];
        normals = new float[count * 3];
        textureCoords = new float[count*2];
        indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
        int vertexPointer = 0;
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = -(float)j/((float)VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = 0;
                vertices[vertexPointer*3+2] = -(float)i/((float)VERTEX_COUNT - 1) * SIZE;
                normals[vertexPointer*3] = 0;
                normals[vertexPointer*3+1] = 1;
                normals[vertexPointer*3+2] = 0;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }
        int pointer = 0;
        for(int gz=0;gz<VERTEX_COUNT-1;gz++){
            for(int gx=0;gx<VERTEX_COUNT-1;gx++){
                int topLeft = (gz*VERTEX_COUNT)+gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }
    
    public Vector3f[] getVertasVec()	{
    	Vector3f[] returnVector = new Vector3f[VERTEX_COUNT * VERTEX_COUNT];
    	int offset = 0;
    	for(int i = 0; i < vertices.length - 3; i+= 3)	{
    		returnVector[offset] = new Vector3f(vertices[i], vertices[i+1], vertices[i+2]);
    		offset++;
    	}
    	return returnVector;
    	
    }
    
    
    public void printVert()		{
		if(vertices != null)	{
			for(int i = 0; i < vertices.length - 3; i += 3)	{
				if(vertices[i+1] != 0)
					System.out.println("Y: " + vertices[i+1]);
			}
		}
		System.out.println("\n");
	}
 
}
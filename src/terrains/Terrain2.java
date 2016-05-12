package terrains;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import renderEngine.Loader;
import textures.ModelTexture;

public class Terrain2 {
	
	
	public static void main(String[] args)	{
		Loader loader = new Loader();
		Terrain2 terrain = new Terrain2(0,0,loader,new ModelTexture(loader.loadTexture("grassNew")));
		terrain.generateTerrain(loader, null);
		terrain.printVert();
	}
	
    
	private static final float SIZE = 1000;
    
    private static final float MAX_HEIGHT = 80;
    private static final float MAX_PIXEL_COLOR = 256 * 256 * 256;
    
    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;
    
    private boolean heightMapUse;
    
    
    private boolean useHeightMap;
    private float[] vertices;
    private float[] normals;
    float[] textureCoords;
    int[] indices;
    Loader loader;
    
    ArrayList<PointNode> leafs;
     
   
    public Terrain2(int gridX, int gridZ, Loader loader, ModelTexture texture, String HeightMap){
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.loader = loader;
        heightMapUse = true;
        leafs = new ArrayList<PointNode>();
        model = generateTerrain(loader, HeightMap);
        useHeightMap = useHeightMap;
        
    }
    
    public Terrain2(int gridX, int gridZ, Loader loader, ModelTexture texture){
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.loader = loader;
        heightMapUse = false;
        leafs = new ArrayList<PointNode>();
        model = generateTerrain(loader, null);
        useHeightMap = useHeightMap;
        
    }
    
    public void addNode(float x, float y, float z)	{
    	PointNode newNode = new PointNode(x,y,z);
    	leafs.add(newNode);
    }
     
     
    public float getX() {
        return x;
    }
 
 
 
    public float getZ() {
        return z;
    }
 
    private Vector3f calculateNormal(int x, int z, BufferedImage image)		{
    	
    	if(x < 0 || x > image.getHeight() || z < 0 || z > image.getHeight())	{
    		return null;
    	}
    	
    	float heightL = getHeight(x-1, z, image);
    	float heightR = getHeight(x+1, z, image);
    	float heightD = getHeight(x, z-1, image);
    	float heightU = getHeight(x, z+1, image);

    	Vector3f normal = new Vector3f(heightL-heightR, 2f, heightD - heightU);
    	normal.normalise();
    	return normal;
    }
    
    
    
    private void calculateNormal2()	{
    	Vector3f p1,p2,p3,v1,v2,normal;
    	Vector3f tmpCoords;
        
        for(int i = 0; i < indices.length - 3; i += 3)	{
        	tmpCoords = leafs.get(indices[i]).getCoordinates();
        	p1 = new Vector3f(tmpCoords.x, tmpCoords.y, tmpCoords.z);
        	
        	tmpCoords = leafs.get(indices[i+1]).getCoordinates();
        	p2 = new Vector3f(tmpCoords.x, tmpCoords.y, tmpCoords.z);
        	
        	tmpCoords = leafs.get(indices[i+2]).getCoordinates();
        	p3 = new Vector3f(tmpCoords.x, tmpCoords.y, tmpCoords.z);
        	
        	//(p2.X - p1.X, p2.Y - p1.Y, p2.Z - p1.Z)
        	v1 = new Vector3f(p2.x - p1.x, p2.y - p1.y, p2.z - p1.z);
        	v2 = new Vector3f(p3.x - p1.x, p3.y - p1.y, p3.z - p1.z);
        	
        	normal = new Vector3f();
        	//normal = v1.crs(v2);
        	Vector3f.cross(v1, v2, normal);
        	normal.normalise();
        	
        	//for vertice 0
        	leafs.get(indices[i]).setNormal(normal);
        	leafs.get(indices[i+1]).setNormal(normal);
        	leafs.get(indices[i]+2).setNormal(normal);
        	/*
        	normals[i] = normal.x;
        	normals[i+1] = normal.y;
        	normals[i+2] = normal.z;
        	*/
        }
    }
 
    public RawModel getModel() {
        return model;
    }
 
 
 
    public ModelTexture getTexture() {
        return texture;
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
    
    public void printGraph()		{
		if(leafs.size() != 0)	{
			for(PointNode leaf : leafs)	{
				leaf.printCoord();
			}
		}
		System.out.println("\n");
	}
    
    private RawModel generateTerrain(Loader loader, String heightMap){
        
    	int VERTEX_COUNT = 0;
    	
    	BufferedImage image = null;
    	if(heightMapUse == true)	{
    		
	    	try {
				image = ImageIO.read(new File("res/" + heightMap +".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	VERTEX_COUNT = image.getHeight();
    	}
    	
    	
    	if(VERTEX_COUNT == 0)	{
    		VERTEX_COUNT = 128;
    	}
    		
    	int count = VERTEX_COUNT * VERTEX_COUNT;
        vertices = new float[count * 3];
        normals = new float[count * 3];
        textureCoords = new float[count*2];
        indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
        int vertexPointer = 0;
        int leafCount = 0;
        
        for(int i=0;i<VERTEX_COUNT;i++){
            for(int j=0;j<VERTEX_COUNT;j++){
                vertices[vertexPointer*3] = -(float)j/((float)VERTEX_COUNT - 1) * SIZE;
                
                if(heightMapUse == true)	{
                	vertices[vertexPointer*3+1] = getHeight(j, i, image);
                }
                else	{
                	vertices[vertexPointer*3+1] = 1;
                }
                
                vertices[vertexPointer*3+2] = -(float)i/((float)VERTEX_COUNT - 1) * SIZE;
                
                addNode(vertices[vertexPointer*3], vertices[vertexPointer*3+1], vertices[vertexPointer*3+2]);
                
                Vector3f normal = calculateNormal(j,i,image);
                
                leafs.get(leafCount).setNormal(normal);
                
                
                
                
                
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
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
    
   
    private float getHeight(int x, int z, BufferedImage image)	{
    	if(x < 0 || x > image.getHeight() || z < 0 || z > image.getHeight())	{
    		return 0;
    	}
    	
    	//System.out.println("X: " + x + " | Z: " + z);
    	if(x >= 256)
    		x = 255;
    	
    	if(z >= 256)
    		z = 255;
    	
    	float height = image.getRGB(x, z);
    	height += MAX_PIXEL_COLOR/2f;
    	height /= MAX_PIXEL_COLOR/2f;
    	
    	height *= MAX_HEIGHT;
    	return height;
    	
    	
    }
    
    public float getHeightSimple(float x, float z)	{
    	
    	for(int i = 0; i < vertices.length; i+= 3)	{
    		if(vertices[i] == x && vertices[i+2] == z){
    			return vertices[i+1];
    		}
    	}
    	return 0;
    	
    }
 
}
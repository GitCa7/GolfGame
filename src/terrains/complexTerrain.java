package terrains;

import java.text.DecimalFormat;

public class complexTerrain {
	
	private float width, length;
	private int spaceInBetween = 6;
	
	float[] vertices, normals, textureCoords;
	int[] indices;
	
	
	
	private boolean setToComplex;
	private boolean centerToZero;
	
	private float posX, posY;
	
	private float vertCount, xVertCount, zVertCount;
	
	public complexTerrain(int width, int length, boolean setToComplex)	{
		this.width = width * 6;
		this.length = length * 6;
		this.setToComplex = setToComplex;
	}
	
	public void setComplexity()	{
		if(setToComplex)	{
			float stepSize = 
		}
	}
	
	
	public void calcTerrain(){
		
	}
	
}

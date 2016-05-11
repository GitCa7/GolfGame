package terrains;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;



public class PointNode {
	
	ArrayList<PointNode> connected;
	int ID;
	private float x,y,z;
	Vector3f normal;
	
	
	public PointNode(float x, float y, float z)	{
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.z = z;
		connected = new ArrayList();
		normal = new Vector3f(0,0,1);
	}
	
	public void setNormal(Vector3f vect)	{
		normal = vect;
	}
	
	public Vector3f getNormal()	{
		return normal;
	}
	
	public void changeHeight(float amount)		{
		if(z < 8f && z > -8f)
			z += amount;
	}
	
	public Vector3f getCoordinates()	{
		return new Vector3f(x,y,z);
	}
	
	public void printCoord()	{
		System.out.println("X: " + x + "\tY: " + y + "\tZ: " + z);
	}

	
	
	public void addConnected(PointNode newNode)	{
		connected.add(newNode);
	}
	
	
	
	

}


package models;

public class RawModel {
	
	private int ID;
	private int vertexCount;
	
	public RawModel(int ID, int vertexCount)	{
		this.ID = ID;
		this.vertexCount = vertexCount;
	}
	
	public int getID()	{
		return ID;
	}
	
	public int getVertexCount()	{
		return vertexCount;
	}
	
	
	public void setID(int newID)	{
		ID = newID;
	}
	
	public void setVertexCount(int newvCount)	{
		vertexCount = newvCount;
	}
	

}

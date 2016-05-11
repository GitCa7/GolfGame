package textures;

public class ModelTexture {

	private int textureID;
	
	private float shineDamper = 1;
	private float reflectivity;
	
	
	private boolean hasTranspercy = false;
	private boolean useFakeLighting = false;
	
	

	public ModelTexture(int textureID)	{
		this.textureID = textureID;
	}
	
	
	
	public boolean isUseFakeLighting() {
		return useFakeLighting;
	}



	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}



	public void setHasTranspercy(boolean hasTranspercy) {
		this.hasTranspercy = hasTranspercy;
	}

	public boolean getHasTranspercy() {
		return hasTranspercy;
	}

	public int getID()	{
		return textureID;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}
}

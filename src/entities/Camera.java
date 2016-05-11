package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
	
	private Vector3f position;
	
	
	private float pitch = 10;
	private float yaw = 0;
	private float roll;

	private float movemntSpeed = 2f;
	
	public Camera(){
		position = new Vector3f(0,5,0);
		
	}

	public Camera(Vector3f position){
		this.position = position;
	}
	
	public void move(){
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			position.z-= movemntSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			position.z+= movemntSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			position.x+= movemntSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			position.x-= movemntSpeed;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			position.y+= 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			position.y-= 0.2f;;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			yaw += 0.6f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			yaw -= 0.6f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			pitch -= 0.2f;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			pitch += 0.2f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_P)){
			System.out.println("X: " + position.x + "\tY: " + position.y + "\tZ: " + position.z);
		}
	}
	
	public void getCursorCoord()	{
		
	}
	

	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	

}

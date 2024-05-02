package conn;

public class Robot_Settings {
	
	int id;
    long speed;
    boolean direction;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getSpeed() {
		return speed;
	}
	public void setSpeed(long speed) {
		this.speed = speed;
	}
	public boolean isDirection() {
		return direction;
	}
	public void setDirection(boolean direction) {
		this.direction = direction;
	}
    
    
	public String getParseString() {
		return this.speed+"#"+direction;
	}

}

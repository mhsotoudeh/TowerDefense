import java.util.Comparator;
import java.util.HashMap;

class Enemy implements Comparator<Enemy>
{
	// Properties
	private int speed;
	private int cost;
	// Comparables
	private int health;
	private int distanceToCastle;
	// Navigation
	private char direction;
	private Pair position;
	private HashMap<Pair, Character> rotatingPoints;
	private int timeToDepart;
	
	public Enemy() {}
	
	public Enemy(int number, int timeToDepart, Trace t)
	{
		// Set personal properties
		switch(number)
		{
		case (15):
			speed = 300;
			cost = 1;
			health = 200;
			break;
		}
		
		// Set navigational properties 
		distanceToCastle = t.getDistance();
		direction = t.getDirection();
		position = new Pair(t.getOrigin().getX(), t.getOrigin().getY());
		rotatingPoints = t.getRotatingPoints();
		this.timeToDepart = timeToDepart;
	}

	public void move(int time)
	{
		if (time % speed == 0)
		{
			if (timeToDepart == 0)
			{
				// Adjust direction, if needed
				if (rotatingPoints.get(position) != null)
					direction = rotatingPoints.get(position);
				// Do movement
				switch (direction)
				{
				case 'r':
					position.increaseX();
					break;
				case 'l':
					position.decreaseX();
					break;
				case 'u':
					position.increaseY();
					break;
				case 'd':
					position.decreaseY();
					break;
				}
								
				distanceToCastle--;
			}
			
			else timeToDepart--;
		}
	}
	
	public void castleAttack(int time)
	{
		if (time % speed == 0)
			if (this.isAtCastle())
				TowerDefense.killEnemy(this);
	}
	
	public void attack(int power)
	{
		health -= power;
		
		if (health <= 0)
			TowerDefense.killEnemy(this);
	}
	
	public Pair getPosition()
	{
		return position;
	}
	
	public int getCost()
	{
		if (health <= 0)
			return cost;
		
		else return 0;
	}
	
	public boolean isAtCastle()
	{
		if (distanceToCastle == 0)
			return true;
		
		else return false;
	}
	
	public boolean isAttackable()
	{
		if (timeToDepart == 0)
			return true;
		
		else return false;
	}
	
	@Override
	public int compare(Enemy e1, Enemy e2)
	{
			 if (e1.health > e2.health) return 1;
		else if (e1.health < e2.health) return -1;
		
		else if (e1.distanceToCastle > e2.distanceToCastle) return 1;
		else if (e1.distanceToCastle < e2.distanceToCastle) return -1;
		
		else return 0;
	}
	
	@Override
    public String toString()
	{
        return String.format("health: " + health + "\n distance: " + distanceToCastle + "\n direction: " + direction + "\n position: " + position);
    }
}

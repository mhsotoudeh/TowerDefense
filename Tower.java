import java.util.ArrayList;
import java.util.Collections;

class Tower
{
	private int reloadTime;
	private int power;
	private int range;
	private int cost;
	private Pair position;
	private ArrayList<Pair> cells;
	private ArrayList<Enemy> enemies;
	
	public Tower(Pair position, int cost)
	{
		// Set personal properties
		this.cost = cost;
		switch(cost)
		{
			case 11:
				reloadTime = 300;
				power = 200;
				range = 7;
				break;
		}
		
		// Fill cells array
		this.position = new Pair (position.getX(), position.getY());
		this.enemies = new ArrayList<Enemy>();
		setCells();
	}
	
	private void setCells()
	{
		this.cells = new ArrayList<Pair>();
		int x = 0 , y = 0;
		for (int j = range ; j > 0 ; j--)
		{
			for (int i = 0 ; i <= j ; i++)
			{
				x = i;
				y = j - x;

				cells.add(new Pair(position.getX() + x, position.getY() + y));
				cells.add(new Pair(position.getX() - x, position.getY() + y));
				cells.add(new Pair(position.getX() + x, position.getY() - y));
				cells.add(new Pair(position.getX() - x, position.getY() - y));

				// Check if tower is near the border and range is incomplete
			} 
		}
	}
	
	public void setEnemies(int time)
	{
		if (time % reloadTime == 0)
		{
			enemies.clear();
			Pair ePos;
			for (Enemy e : TowerDefense.getEnemies())
			{
				ePos = e.getPosition();
				if (cells.contains(ePos))
					enemies.add(e);
			}
			// Check for emptiness
			if (enemies.size() != 0)
				sortAndAttack();
		}
	}
	
	private void sortAndAttack()
	{
		// Sort enemies list
		Collections.sort(enemies, new Enemy());
		
		// Attack
		for (int i = 0 ; i < enemies.size() ; i++)
		{
			if (enemies.get(i).isAttackable())
			{
				enemies.get(i).attack(power);
				break;
			}
		}
		
	}

	public int getCost()
	{
		return cost;
	}


	public Pair getPosition()
	{
		return position;
	}
}

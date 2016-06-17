import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class TowerDefense
{
	private static int money;
	private static int health;
	private static Pair mapDimensions;
	private static ArrayList<Enemy> allEnemies = new ArrayList<Enemy>();
	private static ArrayList<Tower> allTowers = new ArrayList<Tower>();
	private static ArrayList<Trace> allTraces = new ArrayList<Trace>();
	private static ArrayList<Pair> forbiddenPoints = new ArrayList<Pair>();

	private static void increaseMoney(int x)
	{
		money += x;
	}
	
	private static void decreaseMoney(int x)
	{
		money -= x;
	}
	
	private static void generateEnemies(int number)
	{
		for (int i = 0 ; i < number ; i++)
			allEnemies.add(new Enemy(number, i/5, allTraces.get(i%5)));
	}
	
	public static void decreaseHealth()
	{
		health--;
	}
	
	public static ArrayList<Enemy> getEnemies()
	{
		return allEnemies;
	}
	
	public static void killEnemy(Enemy e)
	{
		int i = allEnemies.indexOf(e);
		allEnemies.remove(i);
		
		increaseMoney(e.getCost());
		if (e.isAtCastle() == true)
			decreaseHealth();
	}
	
	public static void buyTower(Pair position, int cost)
	{
		// Check for being inside the map
				if (position.getX() >= mapDimensions.getX() ||
					position.getY() >= mapDimensions.getY() ||
					position.getX() < 0 || position.getY() < 0)
				{
					System.out.println("You cannot build a tower outside the map.");
					return;
				}
		
		// Check forbidden points
		if (forbiddenPoints.contains(position))
		{
			System.out.println("You cannot build a tower in this position.");
			return;
		}
		
		// Check if another tower is not located there
		for (Tower tower : allTowers)
			if (tower.getPosition().equals(position))
			{
				System.out.println("Another tower is already built here.");
				return;
			}
		
		// Check for sufficient money
		if (money >= cost)
		{
			allTowers.add(new Tower(position, cost));
			decreaseMoney(cost);
		}
		
		else System.out.println("Insufficient money to buy this type of tower.");
		
		return;
	}
	
	
	public static void removeTower(Tower t)
	{

		int i = allTowers.indexOf(t);
		allTowers.remove(i);
		
		// Increase money depending on sell or remove
		increaseMoney(t.getCost());
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		// Get map (dimension and traces)
		System.out.println("Enter map dimensions");
		int xMap = sc.nextInt();
		int yMap = sc.nextInt();
		mapDimensions = new Pair(xMap, yMap);
		
		for (int i = 0 ; i < 5 ; i++)
		{
			allTraces.add(new Trace(sc));
			forbiddenPoints.addAll(allTraces.get(i).getForbiddenPoints());
		}
		
		// Get time interval
		System.out.println("Enter time interval.");
		int interval = sc.nextInt();
		
		// Get initial Money and enemy number
		System.out.println("Enter health, then money, then number of enemies.");
		health = sc.nextInt();
		money = sc.nextInt();
		int enemyNumber = sc.nextInt();
		
		// Build Towers
		// Get command
		boolean start = false;
		String command = "";
		int towerX , towerY , towerCost;
		
		while (start == false)
		{
			System.out.println("Enter command to Buy or Start");
			command = sc.next();
			
			if (command.equals("Start"))
//				if (allTowers.size() > 0)
					start = true;
//				else System.out.println("You need to have at least one tower to start the game!");
			
			else if (command.equals("Buy"))
			{
				System.out.println("Please enter tower type (cost)");
				towerCost = sc.nextInt();
				
				System.out.println("Please enter coordinates, x and y");
				towerX = sc.nextInt();
				towerY = sc.nextInt();
				
				buyTower(new Pair(towerX, towerY), towerCost);
			}
		}
		
		// Generate groups of enemies (wave) (Type of enemies is determined by their numbers, at the moment!)
		generateEnemies(enemyNumber);
		
		// Start the game using scheduler
		Timer gameTimer = new Timer();
		// Schedule to run after every interval
	    gameTimer.scheduleAtFixedRate(new GameManager(interval), 1000, interval); 
	}
	
	public static char getChar(Scanner sc)
	{		
		String str = sc.next();
		char ch = str.charAt(0);
		
		return ch;
	}

	
	// Class to time schedule
		static class GameManager extends TimerTask
		{
			private int time = 0;
			private int interval;
			
			public GameManager(int interval)
			{
				this.interval = interval;
			}

		    public void run()
		    {
		        // Move the enemies
		    	// Move the enemies and catle attack
		    	for (int i = 0 ; i < allEnemies.size() ; i++)
		    		allEnemies.get(i).move(time);
		    	for (int i = allEnemies.size()-1 ; i >= 0 ; i--)
		    		allEnemies.get(i).castleAttack(time);
		    	
		    	// Debug Test
		    	System.out.println("");
		    	System.out.println("Enemy positions");
				for (int i = 0 ; i < allEnemies.size() ; i++)
				{
					System.out.println(allEnemies.get(i).getPosition());
				}
				//
		    	
				// Prepare towers and attack
		    	for (int i = 0 ; i < allTowers.size() ; i++)
		    		allTowers.get(i).setEnemies(time);
		    	
		    	System.out.println("Enemies are coming!");
		    	System.out.println("Money: " + money);
		    	System.out.println("Health: " + health);
		    	
		    	time += interval;
		    	
		    	// Check for cancelation
		    	if (health == 0)
		    	{
		    		System.out.println("You Lose!");
		    		this.cancel();
		    	}
		    	
		    	else if (allEnemies.size() == 0)
		    	{
		    		System.out.println("You Won!");
		    		this.cancel();
		    	}
		    	
		    	
		    }

		}
	
}
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

class Trace
{
	private int finalDistance;
	private char initialDirection;
	private Pair origin;
	private HashMap<Pair, Character> rotatingPoints;
	private ArrayList<Pair> pairSet;
	
	public Trace(Scanner sc)
	{	
		System.out.println("Enter origin, x then y");
		int x = sc.nextInt();
		int y = sc.nextInt();
		origin = new Pair(x, y);
		
		System.out.println("Enter initial direction, which is a character");
		initialDirection = TowerDefense.getChar(sc);
		
		System.out.println("Enter distance to castle");
		finalDistance = sc.nextInt();
		
		System.out.println("Enter number of rotation points, "
				+ "then x, then y, then direction");
		int n = sc.nextInt();
		
		rotatingPoints = new HashMap<Pair, Character>();
		pairSet = new ArrayList<Pair>();
		int xBar , yBar;
		char dBar;
		for (int i = 0 ; i < n ; i++)
		{
			// Scan information 
			xBar = sc.nextInt();
			yBar = sc.nextInt();
			dBar = TowerDefense.getChar(sc);
			
			// Add to the map
			rotatingPoints.put(new Pair(xBar, yBar), dBar);
			pairSet.add(new Pair(xBar, yBar));
		}
		
		System.out.println("Trace created successfully.");
	}
	
	public ArrayList<Pair> getForbiddenPoints()
	{
		ArrayList<Pair> result = new ArrayList<Pair>();
		
		// Assuming rotating point are sorted
		int x0 = origin.getX();
		int y0 = origin.getY();
		char d = initialDirection;
		int x , y;
		
		Set<Pair> keySet = rotatingPoints.keySet();
		
		for (int i = 0 ; i < keySet.size() ; i++)
		{
			x = pairSet.get(i).getX();
			y = pairSet.get(i).getY();
			
			switch(d)
			{
			case 'r':
				for (int j = 0 ; j < x-x0 ; j++)
					result.add(new Pair(x0+j, y0));
				break;
			case 'l':
				for (int j = 0 ; j < x0-x ; j++)
					result.add(new Pair(x0-j, y0));
				break;
			case 'u':
				for (int j = 0 ; j < y-y0 ; j++)
					result.add(new Pair(x0, y0+j));
				break;
			case 'd':
				for (int j = 0 ; j < y0-y ; j++)
					result.add(new Pair(x0, y0-j));
				break;
			}
			
			x0 = x;
			y0 = y;
			d = rotatingPoints.get(pairSet.get(i));
		}
		
		return result;
	}
	
	public int getDistance()
	{
		return finalDistance;
	}
	
	public char getDirection()
	{
		return initialDirection;
	}
	
	public Pair getOrigin()
	{
		return origin;
	}
	
	public HashMap<Pair, Character> getRotatingPoints()
	{
		return rotatingPoints;
	}
}

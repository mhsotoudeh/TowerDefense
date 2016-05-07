
class Pair
{
	private int x;
	private int y;
	
	public Pair(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void increaseX()
	{
		x++;
	}
	
	public void increaseY()
	{
		y++;
	}
	
	public void decreaseX()
	{
		x--;
	}
	
	public void decreaseY()
	{
		y--;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public boolean equals(Object o)
	{
		// If the object is compared with itself then return true
		if (o == this)
			return true;
		
		// Check if o is an instance of Pair or not
		if (!(o instanceof Pair))
			return false;
		
		// typecast o to Pair so that we can compare data members
		Pair other = (Pair) o;
		
		// Compare data members and return accordingly
		if (this.x == other.x && this.y == other.y)
			return true;
		
		else return false;
	}
	
	@Override
	public int hashCode()
	{
		int result = 3*x+2*y;
		return result;
	}
	
	@Override
    public String toString()
	{
        return String.format("x = " + x + "\t y = " + y);
    }
	
}

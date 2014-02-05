package no.runsafe.boondries;

public class Boundary
{
	public Boundary(int lowPosition, int highPosition, int maxHeight)
	{
		this.lowPosition = lowPosition;
		this.highPosition = highPosition;
		this.maxHeight = maxHeight;
	}

	public int getLowPosition()
	{
		return lowPosition;
	}

	public int getHighPosition()
	{
		return highPosition;
	}

	public int getMaxHeight()
	{
		return maxHeight;
	}

	private final int lowPosition;
	private final int highPosition;
	private final int maxHeight;
}

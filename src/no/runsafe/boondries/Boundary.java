package no.runsafe.boondries;

public class Boundary
{
	public Boundary(int lowPosition, int highPosition)
	{
		this.lowPosition = lowPosition;
		this.highPosition = highPosition;
	}

	public int getLowPosition()
	{
		return lowPosition;
	}

	public int getHighPosition()
	{
		return highPosition;
	}

	private final int lowPosition;
	private final int highPosition;
}

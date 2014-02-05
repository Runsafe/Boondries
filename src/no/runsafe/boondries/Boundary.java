package no.runsafe.boondries;

import no.runsafe.framework.api.IWorld;

public class Boundary
{
	public Boundary(int lowPosition, int highPosition, IWorld world)
	{
		this.lowPosition = lowPosition;
		this.highPosition = highPosition;
		this.world = world;
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
	private final IWorld world;
}

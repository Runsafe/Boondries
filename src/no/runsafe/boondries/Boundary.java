package no.runsafe.boondries;

import no.runsafe.framework.api.IWorld;

public class Boundary
{
	public Boundary(String name, int lowPosition, int highPosition, IWorld world)
	{
		this.name = name;
		this.lowPosition = lowPosition;
		this.highPosition = highPosition;
		this.world = world;
	}

	public String getName()
	{
		return name;
	}

	public int getLowPosition()
	{
		return lowPosition;
	}

	public int getHighPosition()
	{
		return highPosition;
	}

	private final String name;
	private final int lowPosition;
	private final int highPosition;
	private final IWorld world;
}

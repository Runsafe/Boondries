package no.runsafe.boondries;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.filesystem.IPluginDataFile;
import no.runsafe.framework.api.filesystem.IPluginFileManager;
import no.runsafe.framework.api.log.IConsole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BoundsHandler implements IConfigurationChanged
{
	public BoundsHandler(IPluginFileManager fileManager, IConsole console)
	{
		this.console = console;
		this.boundsFile = fileManager.getFile("bounds.txt");
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		boundaries.clear();
		int boundaryCount = 0;

		for (String boundLine : boundsFile.getLines())
		{
			// Line format: world,lowX,highX,maxHeight
			String[] data = boundLine.split(",");
			String worldName = data[0];

			if (!boundaries.containsKey(worldName))
				boundaries.put(worldName, new ArrayList<Boundary>(1));

			boundaries.get(worldName).add(new Boundary(
					Integer.parseInt(data[1]),
					Integer.parseInt(data[2]),
					Integer.parseInt(data[3])
			));
			boundaryCount++;
		}

		console.logInformation("Loaded %s boundaries in %s worlds.", boundaryCount, boundaries.size());
	}

	private List<Boundary> getWorldBoundaries(IWorld world)
	{
		if (world != null && boundaries.containsKey(world.getName()))
			return boundaries.get(world.getName());

		return Collections.emptyList();
	}

	public boolean isPastBoundary(ILocation location)
	{
		List<Boundary> bounds = getWorldBoundaries(location.getWorld());

		if (!bounds.isEmpty())
			for (Boundary boundary : bounds)
				if (location.getX() > boundary.getHighPosition() || location.getZ() > boundary.getHighPosition() ||
					location.getX() < boundary.getLowPosition() || location.getZ() < boundary.getLowPosition() ||
					location.getY() > boundary.getMaxHeight())
					return true;

		return false;
	}

	private final HashMap<String, List<Boundary>> boundaries = new HashMap<String, List<Boundary>>(0);
	private final IConsole console;
	private final IPluginDataFile boundsFile;
}

package no.runsafe.boondries;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.filesystem.IPluginDataFile;
import no.runsafe.framework.api.filesystem.IPluginFileManager;
import no.runsafe.framework.api.log.IConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BoundsHandler implements IConfigurationChanged
{
	public BoundsHandler(IPluginFileManager fileManager, IServer server, IConsole console)
	{
		this.server = server;
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
			// Line format: world,lowX,highX
			String[] data = boundLine.split(",");
			String worldName = data[0];

			if (!boundaries.containsKey(worldName))
				boundaries.put(worldName, new ArrayList<Boundary>(1));

			boundaries.get(worldName).add(new Boundary(
					Integer.parseInt(data[1]),
					Integer.parseInt(data[2]),
					server.getWorld(worldName)
			));
			boundaryCount++;
		}

		console.logInformation("Loaded %s boundaries in %s worlds.", boundaryCount, boundaries.size());
	}

	private final HashMap<String, List<Boundary>> boundaries = new HashMap<String, List<Boundary>>(0);
	private final IServer server;
	private final IConsole console;
	private final IPluginDataFile boundsFile;
}

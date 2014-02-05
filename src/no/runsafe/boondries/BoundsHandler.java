package no.runsafe.boondries;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.log.IConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoundsHandler implements IConfigurationChanged
{
	public BoundsHandler(IServer server, IConsole console)
	{
		this.server = server;
		this.console = console;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		boundaries.clear();
		Map<String, Map<String, String>> section = configuration.getConfigSectionsAsMap("zones");
		int boundaryCount = 0;

		for (Map.Entry<String, Map<String, String>> node : section.entrySet())
		{
			console.logInformation(node.getKey());
			console.logInformation("Size: " + node.getValue().size());
			Map<String, String> map = node.getValue();
			String worldName = map.get("world");

			if (!boundaries.containsKey(worldName))
				boundaries.put(worldName, new ArrayList<Boundary>(1));

			boundaries.get(worldName).add(new Boundary(
					node.getKey(),
					Integer.parseInt(map.get("low")),
					Integer.parseInt(map.get("high")),
					server.getWorld(worldName)
			));

			boundaryCount++;
		}

		console.logInformation("Loaded %s boundaries in %s worlds.", boundaryCount, boundaries.size());
	}

	private final HashMap<String, List<Boundary>> boundaries = new HashMap<String, List<Boundary>>(0);
	private final IServer server;
	private final IConsole console;
}

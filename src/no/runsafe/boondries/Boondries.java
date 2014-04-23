package no.runsafe.boondries;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.features.Events;

public class Boondries extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		// Framework features
		addComponent(Events.class);

		// Plugin components
		addComponent(BoundsHandler.class);
		addComponent(PlayerMonitor.class);
	}
}

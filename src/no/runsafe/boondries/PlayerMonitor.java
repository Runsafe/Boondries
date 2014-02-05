package no.runsafe.boondries;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerMonitor implements IConfigurationChanged, IPlayerMove
{
	public PlayerMonitor(BoundsHandler handler, IConsole console, IScheduler scheduler)
	{
		this.handler = handler;
		this.console = console;
		this.scheduler = scheduler;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		killTimer = configuration.getConfigValueAsInt("killTimer");
	}

	@Override
	public boolean OnPlayerMove(IPlayer player, ILocation from, ILocation to)
	{
		String playerName = player.getName();
		if (!flaggedPlayers.contains(playerName))
			if (handler.isPastBoundary(to))
				flagPlayer(player);

		return true;
	}

	private void flagPlayer(final IPlayer player)
	{
		String playerName = player.getName();
		flaggedPlayers.add(playerName);
		player.sendColouredMessage("&4You have travelled too far, turn back now or you will be terminated!");
		console.logWarning("Player %s has passed a boundary, preparing to terminate in %s seconds.", playerName, killTimer);
		scheduler.startSyncTask(new Runnable()
		{
			@Override
			public void run()
			{
				if (handler.isPastBoundary(player.getLocation()))
				{
					player.damage(500D); // Die, potato!
					flaggedPlayers.remove(player.getName());
				}
			}
		}, killTimer);
	}

	private int killTimer;
	private final BoundsHandler handler;
	private final List<String> flaggedPlayers = new ArrayList<String>(0);
	private final IConsole console;
	private final IScheduler scheduler;
}

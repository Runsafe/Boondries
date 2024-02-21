package no.runsafe.boondries;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.block.IBlockBreak;
import no.runsafe.framework.api.event.block.IBlockPlace;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerMonitor implements IConfigurationChanged, IPlayerMove, IBlockBreak, IBlockPlace
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
		if (handler.isImmune(player))
			return true;

		if (!flaggedPlayers.contains(player.getUniqueId()) && !player.isDead() && handler.isPastBoundary(to))
				flagPlayer(player);

		return true;
	}

	private void flagPlayer(final IPlayer player)
	{
		final UUID playerUIID = player.getUniqueId();
		flaggedPlayers.add(playerUIID);
		player.sendColouredMessage("&4You have travelled too far, turn back now or you will die!");
		console.logWarning("Player %s has passed a boundary, preparing to terminate in %s seconds.", player.getName(), killTimer);
		scheduler.startSyncTask(() -> {
			ILocation location = player.getLocation();
			if (location != null && handler.isPastBoundary(location))
				player.damage(500D); // Die, potato!

			flaggedPlayers.remove(playerUIID);
		}, killTimer);
	}

	@Override
	public boolean OnBlockBreak(IPlayer player, IBlock block)
	{
		return handleBlockAction(player, block);
	}

	@Override
	public boolean OnBlockPlace(IPlayer player, IBlock block)
	{
		return handleBlockAction(player, block);
	}

	private boolean handleBlockAction(IPlayer player, IBlock block)
	{
		return handler.isImmune(player) || !handler.isPastBoundary(block.getLocation());
	}

	private int killTimer;
	private final BoundsHandler handler;
	private final List<UUID> flaggedPlayers = new ArrayList<>(0);
	private final IConsole console;
	private final IScheduler scheduler;
}

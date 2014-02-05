package no.runsafe.boondries.commands;

import no.runsafe.boondries.BoundsHandler;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerAsyncCommand;
import no.runsafe.framework.api.player.IPlayer;

public class BoundaryImmune extends PlayerAsyncCommand
{
	public BoundaryImmune(IScheduler scheduler, BoundsHandler handler)
	{
		super("boundaryimmune", "Toggle yourself as immune for world boundaries.", "runsafe.boondries.immune", scheduler);
		this.handler = handler;
	}

	@Override
	public String OnAsyncExecute(IPlayer executor, IArgumentList parameters)
	{
		return "&eBoundary immunity " + (handler.toggleImmune(executor) ? "activated" : "deactivated");
	}

	private final BoundsHandler handler;
}

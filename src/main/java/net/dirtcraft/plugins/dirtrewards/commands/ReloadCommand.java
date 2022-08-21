package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.RELOAD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		Utilities.loadConfig();
		sender.sendMessage(Strings.CONFIG_RELOADED);
		return true;
	}
}

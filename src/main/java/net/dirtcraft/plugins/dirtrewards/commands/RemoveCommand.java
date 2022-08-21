package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

public class RemoveCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.REMOVE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length != 2) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/dirtrewards remove <block>");
			return true;
		}

		NamespacedKey namespacedKey = NamespacedKey.fromString(args[1]);
		if (namespacedKey == null) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + "/dirtrewards remove <block>");
			return true;
		}

		if (!RewardsManager.doesBlockExist(new Block(namespacedKey))) {
			sender.sendMessage(Strings.BLOCK_DOES_NOT_EXIST);
			return true;
		}

		DatabaseOperation.removeBlock(namespacedKey, () -> {
			sender.sendMessage(Strings.BLOCK_REMOVED.replace("{block}", namespacedKey.toString()));
			RewardsManager.removeBlock(new Block(namespacedKey));
		});

		return true;
	}
}

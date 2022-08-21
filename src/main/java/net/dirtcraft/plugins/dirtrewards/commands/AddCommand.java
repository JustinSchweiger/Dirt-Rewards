package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCommand {
	public static boolean run(CommandSender sender) {
		if (!sender.hasPermission(Permissions.ADD)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		Player player = (Player) sender;
		if (!player.getInventory().getItemInMainHand().getType().isBlock()) {
			sender.sendMessage(Strings.NO_ITEM);
			return true;
		}

		NamespacedKey namespacedKey = player.getInventory().getItemInMainHand().getType().getKey();
		if (RewardsManager.doesBlockExist(new Block(namespacedKey))) {
			sender.sendMessage(Strings.BLOCK_EXISTS);
			return true;
		}

		RewardsManager.addBlock(new Block(namespacedKey));
		DatabaseOperation.addBlock(new Block(namespacedKey));
		sender.sendMessage(Strings.BLOCK_ADDED.replace("{block}", namespacedKey.toString()));

		return true;
	}
}

package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.CommandReward;
import net.dirtcraft.plugins.dirtrewards.data.MoneyReward;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.CommandCallback;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.MoneyCallback;
import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditCommand {
	private static final List<String> options = new ArrayList<>(Arrays.asList("addCommand", "removeCommand", "addMoney", "removeMoney"));

	public static List<String> getOptions() {
		return options;
	}

	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.EDIT)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		NamespacedKey namespacedKey = NamespacedKey.fromString(args[1]);

		if (args.length < 3 || !RewardsManager.doesBlockExist(new Block(namespacedKey))) {
			sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrewards edit <block> <option> <value(s)>");
			return true;
		}

		if (!options.contains(args[2])) {
			sender.sendMessage(Strings.INVALID_OPTION + args[2]);
			return true;
		}

		String option = args[2].toLowerCase();
		switch (option) {
			case "addcommand":
				if (!Utilities.isInteger(args[3])) {
					sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrewards edit <block> addCommand <chance> <#command#> <\"message\">");
					return true;
				}
				int commandChance = Integer.parseInt(args[3]);
				StringBuilder builder = new StringBuilder();
				for (int i = 4; i < args.length; i++) {
					if (i == args.length - 1) {
						builder.append(ChatColor.stripColor(args[i]));
					} else {
						builder.append(ChatColor.stripColor(args[i])).append(" ");
					}
				}
				String commandAndMessage = builder.toString().replace("/", "");
				String command = StringUtils.substringBetween(commandAndMessage, "#", "#");
				String message = StringUtils.substringBetween(commandAndMessage, "\"", "\"");

				if (command == null || message == null) {
					sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrewards edit <block> addCommand <chance> <#command#> <\"message\">");
					return true;
				}

				addCommand(sender, new CommandReward(namespacedKey, command, commandChance, message));
				break;
			case "removecommand":
				if (!Utilities.isInteger(args[3])) {
					sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrewards edit <block> removeCommand <index>");
					return true;
				}
				int commandIndex = Integer.parseInt(args[3]);
				removeCommand(sender, commandIndex, namespacedKey);
				break;
			case "addmoney":
				if (!Utilities.isInteger(args[3]) || !Utilities.isInteger(args[4]) || !Utilities.isInteger(args[5])) {
					sender.sendMessage(Strings.INVALID_ARGUMENTS_USAGE + ChatColor.RED + "/dirtrewards edit <block> addMoney <chance> <min> <max>");
					return true;
				}

				int moneyChance = Integer.parseInt(args[3]);
				int moneyMin = Integer.parseInt(args[4]);
				int moneyMax = Integer.parseInt(args[5]);

				if (moneyMin > moneyMax) {
					moneyMin = moneyMax;
				}

				if (moneyChance < 0 || moneyChance > 100) {
					sender.sendMessage(Strings.CHANCE_BETWEEN_0_AND_100);
					return true;
				}

				addMoney(sender, moneyChance, moneyMin, moneyMax, namespacedKey);
				break;
			case "removemoney":
				removeMoney(sender, namespacedKey);
				break;
		}

		return true;
	}


	private static void removeMoney(CommandSender sender, NamespacedKey namespacedKey) {
		if (!RewardsManager.doesMoneyRewardExist(namespacedKey)) {
			sender.sendMessage(Strings.MONEY_REWARD_DOES_NOT_EXIST);
			return;
		}

		DatabaseOperation.removeMoneyReward(namespacedKey, (MoneyCallback) () -> {
			sender.sendMessage(Strings.MONEY_REWARD_REMOVED);
			RewardsManager.removeMoneyReward(namespacedKey);
		});
	}

	private static void addMoney(CommandSender sender, int chance, int min, int max, NamespacedKey namespacedKey) {
		if (RewardsManager.doesMoneyRewardExist(namespacedKey)) {
			sender.sendMessage(Strings.MONEY_REWARD_ALREADY_EXISTS);
			return;
		}

		MoneyReward moneyReward = new MoneyReward(namespacedKey, chance, min, max);
		DatabaseOperation.addMoneyReward(moneyReward, () -> {
			sender.sendMessage(Strings.MONEY_REWARD_ADDED);
			RewardsManager.addMoneyReward(moneyReward);
		});
	}

	private static void removeCommand(CommandSender sender, int index, NamespacedKey namespacedKey) {
		List<CommandReward> commands = RewardsManager.getCommandRewards(new Block(namespacedKey));
		if (index < 0 || index >= commands.size()) {
			sender.sendMessage(Strings.INVALID_INDEX);
			return;
		}

		CommandReward command = commands.get(index);
		DatabaseOperation.removeCommand(command, () -> {
			sender.sendMessage(Strings.COMMAND_REMOVED);
			RewardsManager.removeCommandReward(command);
		});
	}

	private static void addCommand(CommandSender sender, CommandReward commandReward) {
		if (RewardsManager.doesCommandRewardExist(commandReward)) {
			sender.sendMessage(Strings.COMMAND_REWARD_ALREADY_EXISTS);
			return;
		}

		DatabaseOperation.addCommandReward(commandReward, () -> {
			sender.sendMessage(Strings.COMMAND_REWARD_ADDED);
			RewardsManager.addCommandReward(commandReward);
		});
	}
}

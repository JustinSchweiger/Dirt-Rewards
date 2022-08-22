package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.CommandReward;
import net.dirtcraft.plugins.dirtrewards.data.MoneyReward;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import net.dirtcraft.plugins.dirtrewards.utils.gradient.GradientHandler;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListCommand {
	public static boolean run(CommandSender sender, String[] args) {
		if (!sender.hasPermission(Permissions.LIST)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (args.length == 1) {
			showList(sender, 1);
			return true;
		}

		if (args.length == 2 && Utilities.isInteger(args[1])) {
			showList(sender, Integer.parseInt(args[1]));
			return true;
		}

		return true;
	}

	private static void showList(CommandSender sender, int page) {
		if (page < 1) {
			sender.sendMessage(Strings.INVALID_PAGE);
			return;
		}

		List<Block> blocks = RewardsManager.getBlocks();
		System.out.println(blocks.size());
		if (blocks.size() == 0) {
			sender.sendMessage(Strings.NO_BLOCKS);
			return;
		}

		int maxPage = (int) Math.ceil((double) blocks.size() / (double) Utilities.config.general.listEntries);
		if (page > maxPage) {
			sender.sendMessage(Strings.PAGE_DOES_NOT_EXIST);
			return;
		}

		int start = (page - 1) * Utilities.config.general.listEntries;
		int end = page * Utilities.config.general.listEntries;
		if (end > blocks.size()) {
			end = blocks.size();
		}

		sender.sendMessage(Strings.BAR_TOP);
		sender.sendMessage("");

		for (int i = start; i < end; i++) {
			List<CommandReward> commandRewards = RewardsManager.getCommandRewards(blocks.get(i));
			StringBuilder commandsString = new StringBuilder();
			for (int index = 0; index < commandRewards.size(); index++) {
				String commandLine = ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + index + ChatColor.DARK_GRAY + "] [" + ChatColor.GREEN + commandRewards.get(index).getChance() + "%" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "\"" + ChatColor.YELLOW + commandRewards.get(index).getCommand() + ChatColor.RED + "\"";
				commandsString.append(commandLine);
			}
			String commands = commandsString.toString();
			if (commands.isEmpty()) {
				commands = ChatColor.GRAY + "  No command rewards set.";
			}

			MoneyReward moneyReward = RewardsManager.getMoneyReward(blocks.get(i));
			String money;
			if (moneyReward == null) {
				money = ChatColor.GRAY + "  No money rewards set.";
			} else {
				money = ChatColor.DARK_GRAY + "  [" + ChatColor.GREEN + moneyReward.getChance() + "%" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Min: " + ChatColor.GREEN + moneyReward.getMinBal() + ChatColor.RED + " Max: " + ChatColor.GREEN + moneyReward.getMaxBal();
			}

			BaseComponent[] removeReward = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "\u2715" + ChatColor.DARK_GRAY + "]")
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrewards remove " + blocks.get(i).getNamespacedKey()))
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "Remove this reward completely."))).create();

			BaseComponent[] addCommand = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "\u002B" + ChatColor.DARK_GRAY + "]")
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.BLUE + "Add command.")))
					.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards edit " + blocks.get(i).getNamespacedKey().toString() + " addCommand <chance> <#command#> <\"message\">")).create();

			BaseComponent[] removeCommand = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "\u2212" + ChatColor.DARK_GRAY + "]")
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.BLUE + "Remove command.")))
					.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards edit " + blocks.get(i).getNamespacedKey().toString() + " removeCommand <index>")).create();

			BaseComponent[] addMoney = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u002B" + ChatColor.DARK_GRAY + "]")
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Add money.")))
					.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards edit " + blocks.get(i).getNamespacedKey().toString() + " addMoney <chance> <min> <max>")).create();

			BaseComponent[] removeMoney = new ComponentBuilder("")
					.append(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "\u2212" + ChatColor.DARK_GRAY + "]")
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Remove money.")))
					.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/dirtrewards edit " + blocks.get(i).getNamespacedKey().toString() + " removeMoney")).create();

			BaseComponent[] rewardBlock = new ComponentBuilder("")
					.append(ChatColor.GOLD + blocks.get(i).getNamespacedKey().toString())
					.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(
							ChatColor.GOLD + "ID" + ChatColor.DARK_GRAY + ": " + ChatColor.RED + blocks.get(i).getNamespacedKey().toString() + "\n" +
									"\n" +
									ChatColor.BLUE + "\u2219 " + ChatColor.UNDERLINE + "Commands" + ChatColor.DARK_GRAY + ":" + "\n" +
									commands + "\n" +
									"\n" +
									ChatColor.GREEN + "\u2219 " + ChatColor.UNDERLINE + "Money" + ChatColor.DARK_GRAY + ":" + "\n" +
									money
					))).create();

			ComponentBuilder entry = new ComponentBuilder("");

			if (sender.hasPermission(Permissions.REMOVE)) {
				entry.append(removeReward);
			}

			if (sender.hasPermission(Permissions.EDIT)) {
				entry.append(ChatColor.GRAY + " - ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(addCommand);
				entry.append(" ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(removeCommand);
				entry.append(" ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(addMoney);
				entry.append(" ").event((HoverEvent) null).event((ClickEvent) null);
				entry.append(removeMoney);
			}
			entry.append(ChatColor.GRAY + " - ").event((HoverEvent) null).event((ClickEvent) null);
			entry.append(rewardBlock);
			sender.spigot().sendMessage(entry.create());
		}

		TextComponent bottomBar = new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("-----------------------", new java.awt.Color(251, 121, 0), new java.awt.Color(247, 0, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH)));
		TextComponent pagePrev;
		if (page == 1) {
			pagePrev = new TextComponent(ChatColor.GRAY + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the first page!")));
		} else {
			pagePrev = new TextComponent(ChatColor.GREEN + "  \u25C0 ");
			pagePrev.setBold(true);
			pagePrev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Previous page")));
			pagePrev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rewards list " + (page - 1)));
		}
		bottomBar.addExtra(pagePrev);
		TextComponent pageNext;
		if (page == maxPage) {
			pageNext = new TextComponent(ChatColor.GRAY + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.RED + "You are already on the last page!")));
		} else {
			pageNext = new TextComponent(ChatColor.GREEN + " \u25B6  ");
			pageNext.setBold(true);
			pageNext.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GREEN + "Next page")));
			pageNext.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rewards list " + (page + 1)));
		}
		bottomBar.addExtra(pageNext);
		bottomBar.addExtra(new TextComponent(TextComponent.fromLegacyText(GradientHandler.hsvGradient("-----------------------", new java.awt.Color(247, 0, 0), new java.awt.Color(251, 121, 0), GradientHandler::linear, net.md_5.bungee.api.ChatColor.STRIKETHROUGH))));
		sender.sendMessage("");
		sender.spigot().sendMessage(bottomBar);
	}
}

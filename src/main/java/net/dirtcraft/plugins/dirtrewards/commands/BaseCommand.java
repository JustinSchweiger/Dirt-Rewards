package net.dirtcraft.plugins.dirtrewards.commands;

import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.utils.Permissions;
import net.dirtcraft.plugins.dirtrewards.utils.Strings;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BaseCommand implements CommandExecutor, TabCompleter {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission(Permissions.BASE)) {
			sender.sendMessage(Strings.NO_PERMISSION);
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Strings.NO_CONSOLE);
			return true;
		}

		if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("help"))) {
			List<TextComponent> listings = getListings(sender);
			sender.sendMessage(Strings.BAR_TOP);
			sender.sendMessage("");
			for (TextComponent listing : listings) {
				sender.spigot().sendMessage(listing);
			}
			sender.sendMessage("");
			sender.sendMessage(Strings.BAR_BOTTOM);

			return true;
		}

		String arg = args[0].toLowerCase();
		switch (arg) {
			case "add":
				return AddCommand.run(sender);
			case "list":
				return ListCommand.run(sender, args);
			case "remove":
				return RemoveCommand.run(sender, args);
			case "edit":
				return EditCommand.run(sender, args);
			case "reload":
				return ReloadCommand.run(sender, args);
			default:
				sender.sendMessage(Strings.UNKNOWN_COMMAND);
				return true;
		}
	}

	private List<TextComponent> getListings(CommandSender sender) {
		List<TextComponent> listings = new ArrayList<>();

		if (sender.hasPermission(Permissions.ADD)) {
			TextComponent add = new TextComponent(ChatColor.GOLD + "  /dirtrewards " + ChatColor.YELLOW + "add");
			add.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Adds a reward for the block you are holding in your hand.")));
			add.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtlimit add"));
			listings.add(add);
		}

		if (sender.hasPermission(Permissions.EDIT)) {
			TextComponent editLimit = new TextComponent(ChatColor.GOLD + "  /dirtrewards " + ChatColor.YELLOW + "edit <block> <options> <value>");
			editLimit.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Edits the options of a block.")));
			editLimit.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards edit <block> <option> <value>"));
			listings.add(editLimit);
		}

		if (sender.hasPermission(Permissions.LIST)) {
			TextComponent list = new TextComponent(ChatColor.GOLD + "  /dirtrewards " + ChatColor.YELLOW + "list [page]");
			list.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "List all blocks that have a reward.")));
			list.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards list [page]"));
			listings.add(list);
		}

		if (sender.hasPermission(Permissions.REMOVE)) {
			TextComponent resetPlayer = new TextComponent(ChatColor.GOLD + "  /dirtrewards " + ChatColor.YELLOW + "remove <block>");
			resetPlayer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Use this to completely remove the rewards for a block.")));
			resetPlayer.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards remove <block>"));
			listings.add(resetPlayer);
		}

		if (sender.hasPermission(Permissions.RELOAD)) {
			TextComponent reload = new TextComponent(ChatColor.GOLD + "  /dirtrewards " + ChatColor.YELLOW + "reload");
			reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ChatColor.GRAY + "Reloads the config.")));
			reload.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/dirtrewards reload"));
			listings.add(reload);
		}

		return listings;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> arguments = new ArrayList<>();

		if (args.length == 1) {
			if (sender.hasPermission(Permissions.ADD)) {
				arguments.add("add");
			}

			if (sender.hasPermission(Permissions.LIST)) {
				arguments.add("list");
			}

			if (sender.hasPermission(Permissions.EDIT)) {
				arguments.add("edit");
			}

			if (sender.hasPermission(Permissions.REMOVE)) {
				arguments.add("remove");
			}

			if (sender.hasPermission(Permissions.RELOAD)) {
				arguments.add("reload");
			}
		} else if (args.length == 2 && Utilities.isInteger(args[1]) && args[0].equalsIgnoreCase("list")) {
			arguments.add("[page]");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
			for (Block block : RewardsManager.getBlocks()) {
				arguments.add(block.getNamespacedKey().toString());
			}
		} else if (args.length == 3 && args[0].equalsIgnoreCase("edit")) {
			arguments.addAll(EditCommand.getOptions());
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addCommand")) {
			arguments.add("<chance>");
		} else if (args.length > 4 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addCommand") && !Utilities.containsChar(args, '"')) {
			arguments.add("<#command#>");
		} else if (args.length > 5 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addCommand")) {
			arguments.add("<\"message\">");
		} else if (args.length > 3 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("removeCommand")) {
			NamespacedKey namespacedKey = NamespacedKey.fromString(args[1]);
			int size = RewardsManager.getCommandRewards(new Block(namespacedKey)).size();
			for (int i = 0; i < size; i++) {
				arguments.add(Integer.toString(i));
			}
		} else if (args.length == 4 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addMoney")) {
			arguments.add("<chance>");
		} else if (args.length == 5 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addMoney")) {
			arguments.add("<min>");
		} else if (args.length == 6 && args[0].equalsIgnoreCase("edit") && args[2].equalsIgnoreCase("addMoney")) {
			arguments.add("<max>");
		} else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			for (Block block : RewardsManager.getBlocks()) {
				arguments.add(block.getNamespacedKey().toString());
			}
		}

		List<String> tabResults = new ArrayList<>();
		for (String argument : arguments) {
			if (argument.equalsIgnoreCase("<amount>") || argument.equalsIgnoreCase("[page]") || argument.equalsIgnoreCase("<#command#>") || argument.equalsIgnoreCase("<\"message\">") || argument.equalsIgnoreCase("<chance>")) {
				tabResults.add(argument);
				continue;
			}

			if (argument.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
				tabResults.add(argument);
			}
		}

		return tabResults;
	}
}

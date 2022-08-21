package net.dirtcraft.plugins.dirtrewards.utils;

import org.bukkit.ChatColor;

public class Strings {
	// ---------------------------------------------------------- GENERAL ----------------------------------------------------------
	public static final String PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.YELLOW + "Rewards" + ChatColor.GRAY + "] ";
	public static final String INTERNAL_PREFIX = ChatColor.GRAY + "[" + ChatColor.RED + "Dirt" + ChatColor.YELLOW + "Rewards" + ChatColor.GRAY + "] ";
	public static final String NO_PERMISSION = PREFIX + ChatColor.RED + "You do not have permission to use this command.";
	public static final String NO_CONSOLE = PREFIX + ChatColor.RED + "You must be a player to use this command.";
	public static final String INVALID_ARGUMENTS_USAGE = PREFIX + ChatColor.DARK_RED + "Invalid arguments. \nUsage: ";
	public static final String INVALID_OPTION = PREFIX + ChatColor.RED + "Invalid option: ";
	public static final String NO_ITEM = PREFIX + ChatColor.RED + "You can only add rewards for blocks that can be broken.";
	public static final String UNKNOWN_COMMAND = PREFIX + ChatColor.RED + "Unknown command.";
	public static final String CONFIG_RELOADED = PREFIX + ChatColor.GREEN + "Config reloaded.";
	public static final String MONEY_REWARD = ChatColor.GREEN + "You have been rewarded " + ChatColor.GOLD + "${amount}" + ChatColor.GREEN + ".";
	public static final String BAR_TOP = Utilities.format("&x&f&b&7&9&0&0&m-&x&f&b&7&1&0&0&m-&x&f&a&6&9&0&0&m-&x&f&a&6&1&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&5&1&0&0&m-&x&f&9&4&9&0&0&m-&x&f&9&4&1&0&0&m-&x&f&9&3&8&0&0&m-&x&f&9&3&0&0&0&m-&x&f&8&2&8&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&1&8&0&0&m-&x&f&8&1&0&0&0&m-&x&f&7&0&8&0&0&m-&x&f&7&0&0&0&0&m-" + ChatColor.GRAY + "[ " + ChatColor.RED + "DirtCraft " + ChatColor.YELLOW + "Rewards" + ChatColor.RESET + ChatColor.GRAY + " ]" + "&x&f&7&0&0&0&0&m-&x&f&7&0&8&0&0&m-&x&f&8&1&0&0&0&m-&x&f&8&1&8&0&0&m-&x&f&8&2&0&0&0&m-&x&f&8&2&8&0&0&m-&x&f&9&3&0&0&0&m-&x&f&9&3&8&0&0&m-&x&f&9&4&1&0&0&m-&x&f&9&4&9&0&0&m-&x&f&a&5&1&0&0&m-&x&f&a&5&9&0&0&m-&x&f&a&6&1&0&0&m-&x&f&a&6&9&0&0&m-&x&f&b&7&1&0&0&m-&x&f&b&7&9&0&0&m-");
	public static final String BAR_BOTTOM = Utilities.format("&x&f&f&7&a&0&0&m-&x&f&f&7&5&0&0&m-&x&f&f&7&0&0&0&m-&x&f&f&6&b&0&0&m-&x&f&e&6&6&0&0&m-&x&f&e&6&2&0&0&m-&x&f&e&5&d&0&0&m-&x&f&e&5&8&0&0&m-&x&f&e&5&3&0&0&m-&x&f&e&4&e&0&0&m-&x&f&d&4&9&0&0&m-&x&f&d&4&4&0&0&m-&x&f&d&3&f&0&0&m-&x&f&d&3&b&0&0&m-&x&f&d&3&6&0&0&m-&x&f&d&3&1&0&0&m-&x&f&c&2&c&0&0&m-&x&f&c&2&7&0&0&m-&x&f&c&2&2&0&0&m-&x&f&c&1&d&0&0&m-&x&f&c&1&8&0&0&m-&x&f&c&1&4&0&0&m-&x&f&b&0&f&0&0&m-&x&f&b&0&a&0&0&m-&x&f&b&0&5&0&0&m-&x&f&b&0&0&0&0&m-&x&f&b&0&0&0&0&m-&x&f&b&0&5&0&0&m-&x&f&b&0&a&0&0&m-&x&f&b&0&f&0&0&m-&x&f&c&1&4&0&0&m-&x&f&c&1&8&0&0&m-&x&f&c&1&d&0&0&m-&x&f&c&2&2&0&0&m-&x&f&c&2&7&0&0&m-&x&f&c&2&c&0&0&m-&x&f&d&3&1&0&0&m-&x&f&d&3&6&0&0&m-&x&f&d&3&b&0&0&m-&x&f&d&3&f&0&0&m-&x&f&d&4&4&0&0&m-&x&f&d&4&9&0&0&m-&x&f&e&4&e&0&0&m-&x&f&e&5&3&0&0&m-&x&f&e&5&8&0&0&m-&x&f&e&5&d&0&0&m-&x&f&e&6&2&0&0&m-&x&f&e&6&6&0&0&m-&x&f&f&6&b&0&0&m-&x&f&f&7&0&0&0&m-&x&f&f&7&5&0&0&m-&x&f&f&7&a&0&0&m-");
	public static final String HALF_BAR_ONE = "&x&f&b&7&9&0&0&m-&x&f&b&7&3&0&0&m-&x&f&b&6&c&0&0&m-&x&f&b&6&6&0&0&m-&x&f&b&6&0&0&0&m-&x&f&b&5&9&0&0&m-&x&f&b&5&3&0&0&m-&x&f&b&4&c&0&0&m-&x&f&b&4&6&0&0&m-&x&f&b&4&0&0&0&m-&x&f&b&3&9&0&0&m-&x&f&b&3&3&0&0&m-&x&f&b&2&d&0&0&m-&x&f&b&2&6&0&0&m-&x&f&b&2&0&0&0&m-&x&f&b&1&9&0&0&m-&x&f&b&1&3&0&0&m-&x&f&b&0&d&0&0&m-&x&f&b&0&6&0&0&m-&x&f&b&0&0&0&0&m-";
	public static final String HALF_BAR_TWO = Utilities.format("&x&f&b&0&0&0&0&m-&x&f&b&0&6&0&0&m-&x&f&b&0&d&0&0&m-&x&f&b&1&3&0&0&m-&x&f&b&1&9&0&0&m-&x&f&b&2&0&0&0&m-&x&f&b&2&6&0&0&m-&x&f&b&2&d&0&0&m-&x&f&b&3&3&0&0&m-&x&f&b&3&9&0&0&m-&x&f&b&4&0&0&0&m-&x&f&b&4&6&0&0&m-&x&f&b&4&c&0&0&m-&x&f&b&5&3&0&0&m-&x&f&b&5&9&0&0&m-&x&f&b&6&0&0&0&m-&x&f&b&6&6&0&0&m-&x&f&b&6&c&0&0&m-&x&f&b&7&3&0&0&m-&x&f&b&7&9&0&0&m-");

	// ---------------------------------------------------------- ADD COMMAND ----------------------------------------------------------
	public static final String BLOCK_EXISTS = PREFIX + ChatColor.RED + "This block is already added to the rewards list.";
	public static final String BLOCK_ADDED = PREFIX + ChatColor.GRAY + "Block " + ChatColor.DARK_AQUA + "{block}" + ChatColor.GRAY + " has been added to the rewards list.";

	// ---------------------------------------------------------- LIST COMMAND ----------------------------------------------------------
	public static final String INVALID_PAGE = PREFIX + ChatColor.RED + "Invalid page number.";
	public static final String NO_BLOCKS = PREFIX + ChatColor.RED + "There are no blocks in the rewards list.";
	public static final String PAGE_DOES_NOT_EXIST = PREFIX + ChatColor.RED + "Page does not exist.";

	// ---------------------------------------------------------- EDIT COMMAND ----------------------------------------------------------
	public static final String COMMAND_REWARD_ALREADY_EXISTS = PREFIX + ChatColor.RED + "This command already exists for this block.";
	public static final String COMMAND_REWARD_ADDED = PREFIX + ChatColor.GRAY + "Command has been added!";
	public static final String INVALID_INDEX = PREFIX + ChatColor.RED + "Invalid index.";
	public static final String COMMAND_REMOVED = PREFIX + ChatColor.GRAY + "Command has been removed!";
	public static final String CHANCE_BETWEEN_0_AND_100 = PREFIX + ChatColor.RED + "Chance must be between 0 and 100.";
	public static final String MONEY_REWARD_ALREADY_EXISTS = PREFIX + ChatColor.RED + "There can only be one money reward per block.";
	public static final String MONEY_REWARD_ADDED = PREFIX + ChatColor.GRAY + "Money reward has been added!";
	public static final String MONEY_REWARD_DOES_NOT_EXIST = PREFIX + ChatColor.RED + "There is no money reward for this block.";
	public static final String MONEY_REWARD_REMOVED = PREFIX + ChatColor.GRAY + "Money reward has been removed!";

	// ---------------------------------------------------------- REMOVE COMMAND ----------------------------------------------------------
	public static final String BLOCK_DOES_NOT_EXIST = PREFIX + ChatColor.RED + "This block does not exist in the rewards list.";
	public static final String BLOCK_REMOVED = PREFIX + ChatColor.GRAY + "Block " + ChatColor.DARK_AQUA + "{block}" + ChatColor.GRAY + " has been removed from the rewards table!";
}

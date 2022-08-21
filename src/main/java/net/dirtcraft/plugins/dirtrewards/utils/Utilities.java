package net.dirtcraft.plugins.dirtrewards.utils;

import com.moandjiezana.toml.Toml;
import net.dirtcraft.plugins.dirtrewards.DirtRewards;
import net.dirtcraft.plugins.dirtrewards.commands.BaseCommand;
import net.dirtcraft.plugins.dirtrewards.config.Config;
import net.dirtcraft.plugins.dirtrewards.listener.BlockBreakListener;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Random;
import java.util.logging.Level;

public class Utilities {
	public static Config config;

	public static void loadConfig() {
		if (!DirtRewards.getPlugin().getDataFolder().exists()) {
			DirtRewards.getPlugin().getDataFolder().mkdirs();
		}
		File file = new File(DirtRewards.getPlugin().getDataFolder(), "config.toml");
		if (!file.exists()) {
			try {
				Files.copy(DirtRewards.getPlugin().getResource("config.toml"), file.toPath());
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		config = new Toml(new Toml().read(DirtRewards.getPlugin().getResource("config.toml"))).read(file).to(Config.class);
	}

	public static void registerListener() {
		DirtRewards.getPlugin().getServer().getPluginManager().registerEvents(new BlockBreakListener(), DirtRewards.getPlugin());
	}

	public static void registerCommands() {
		DirtRewards.getPlugin().getCommand("dirtrewards").setExecutor(new BaseCommand());
		DirtRewards.getPlugin().getCommand("dirtrewards").setTabCompleter(new BaseCommand());
	}

	public static void log(Level level, String msg) {
		String consoleMessage;
		if (Level.INFO.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.WHITE + msg;
		} else if (Level.WARNING.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.YELLOW + msg;
		} else if (Level.SEVERE.equals(level)) {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.RED + msg;
		} else {
			consoleMessage = Strings.INTERNAL_PREFIX + ChatColor.GRAY + msg;
		}

		if (!config.general.coloredDebug) {
			consoleMessage = ChatColor.stripColor(msg);
		}

		DirtRewards.getPlugin().getServer().getConsoleSender().sendMessage(consoleMessage);
	}

	public static void disablePlugin() {
		DirtRewards.getPlugin().getServer().getPluginManager().disablePlugin(DirtRewards.getPlugin());
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException | NullPointerException e) {
			return false;
		}

		return true;
	}

	public static String format(String message) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message);
	}

	public static boolean getChance(int minimalChance) {
		Random random = new Random();
		return random.nextInt(99) + 1 <= minimalChance;
	}

	public static void playRewardSound(CommandSender sender) {
		if (!(sender instanceof Player)) {
			return;
		}

		Player player = (Player) sender;
		if (Utilities.config.sound.playRewardSound) {
			String sound = Utilities.config.sound.rewardSound;
			if (sound == null) {
				sound = "minecraft:block.chain.place";
			}
			player.playSound(player.getLocation(), sound, 1, 1);
		}
	}
}

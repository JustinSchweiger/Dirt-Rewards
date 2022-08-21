package net.dirtcraft.plugins.dirtrewards;

import net.dirtcraft.plugins.dirtrewards.data.RewardsManager;
import net.dirtcraft.plugins.dirtrewards.database.Database;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class DirtRewards extends JavaPlugin {

	private static DirtRewards plugin;
	private static Economy econ = null;

	public static DirtRewards getPlugin() {
		return plugin;
	}

	public static Economy getEcon() {
		return econ;
	}

	@Override
	public void onEnable() {
		plugin = this;
		Utilities.loadConfig();
		RewardsManager.init();
		Database.initialiseDatabase();
		Utilities.registerListener();
		Utilities.registerCommands();

		if (!setupEconomy() ) {
			Utilities.log(Level.SEVERE, "Vault not found, disabling plugin");
			Utilities.disablePlugin();
		}
	}

	@Override
	public void onDisable() {
		Database.closeDatabase();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return true;
	}
}

package net.dirtcraft.plugins.dirtrewards.database;

import net.dirtcraft.plugins.dirtrewards.DirtRewards;
import net.dirtcraft.plugins.dirtrewards.data.Block;
import net.dirtcraft.plugins.dirtrewards.data.CommandReward;
import net.dirtcraft.plugins.dirtrewards.data.MoneyReward;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.BlockCallback;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.CommandCallback;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.InitCallback;
import net.dirtcraft.plugins.dirtrewards.database.callbacks.MoneyCallback;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperation {

	public static void addBlock(final Block block) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO BLOCK VALUES (?)")) {
				statement.setString(1, block.getNamespacedKey().toString());
				statement.execute();
			} catch (SQLException ignored) {}
		});
	}

	public static void addCommandReward(final CommandReward commandReward, final CommandCallback commandCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO COMMANDREWARD VALUES (?, ?, ?, ?)")) {
				statement.setString(1, commandReward.getBlockNameSpacedKey().toString());
				statement.setInt(2, commandReward.getChance());
				statement.setString(3, commandReward.getCommand());
				statement.setString(4, commandReward.getMessage());
				statement.execute();
				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), commandCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void removeCommand(final CommandReward command, final CommandCallback commandCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("DELETE FROM COMMANDREWARD WHERE block_namespacedKey = ? AND commandreward_command = ?")) {
				statement.setString(1, command.getBlockNameSpacedKey().toString());
				statement.setString(2, command.getCommand());
				statement.execute();
				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), commandCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void getData(InitCallback initCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement getBlocks = connection.prepareStatement("SELECT * FROM BLOCK");
			     PreparedStatement getCommands = connection.prepareStatement("SELECT * FROM COMMANDREWARD");
			     PreparedStatement getMoney = connection.prepareStatement("SELECT * FROM MONEYREWARD")) {
				ResultSet blocksResult = getBlocks.executeQuery();
				ResultSet commandsResult = getCommands.executeQuery();
				ResultSet moneyResult = getMoney.executeQuery();

				List<Block> blocks = new ArrayList<>();
				List<CommandReward> commands = new ArrayList<>();
				List<MoneyReward> money = new ArrayList<>();

				while (blocksResult.next()) {
					blocks.add(
							new Block(
									NamespacedKey.fromString(blocksResult.getString("block_namespacedKey"))
							)
					);
				}
				while (commandsResult.next()) {
					commands.add(
							new CommandReward(
									NamespacedKey.fromString(commandsResult.getString("block_namespacedKey")),
									commandsResult.getString("commandreward_command"),
									commandsResult.getInt("commandreward_chance"),
									commandsResult.getString("commandreward_message")
							)
					);
				}
				while (moneyResult.next()) {
					money.add(
							new MoneyReward(
									NamespacedKey.fromString(moneyResult.getString("block_namespacedKey")),
									moneyResult.getInt("moneyreward_chance"),
									moneyResult.getInt("moneyreward_bal_min"),
									moneyResult.getInt("moneyreward_bal_max")
							)
					);
				}

				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), () -> initCallback.onSuccess(blocks, commands, money));
			} catch (SQLException ignored) {}
		});
	}

	public static void addMoneyReward(final MoneyReward moneyReward, final MoneyCallback moneyCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("INSERT INTO MONEYREWARD VALUES (?, ?, ?, ?)")) {
				statement.setString(1, moneyReward.getBlockNameSpacedKey().toString());
				statement.setInt(2, moneyReward.getChance());
				statement.setInt(3, moneyReward.getMinBal());
				statement.setInt(4, moneyReward.getMaxBal());
				statement.execute();
				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), moneyCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void removeMoneyReward(final NamespacedKey namespacedKey, final MoneyCallback moneyCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("DELETE FROM MONEYREWARD WHERE block_namespacedKey = ?")) {
				statement.setString(1, namespacedKey.toString());
				statement.execute();
				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), moneyCallback::onSuccess);
			} catch (SQLException ignored) {}
		});
	}

	public static void removeBlock(final NamespacedKey namespacedKey, final BlockCallback blockCallback) {
		Bukkit.getScheduler().runTaskAsynchronously(DirtRewards.getPlugin(), () -> {
			try (Connection connection = Database.getConnection();
			     PreparedStatement statement = connection.prepareStatement("DELETE FROM BLOCK WHERE block_namespacedKey = ?")) {
				statement.setString(1, namespacedKey.toString());
				statement.execute();
				Bukkit.getScheduler().runTask(DirtRewards.getPlugin(), blockCallback::onSuccess);
			} catch (SQLException e) { e.printStackTrace();}
		});
	}
}
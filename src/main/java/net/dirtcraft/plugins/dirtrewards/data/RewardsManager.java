package net.dirtcraft.plugins.dirtrewards.data;

import net.dirtcraft.plugins.dirtrewards.database.DatabaseOperation;
import net.dirtcraft.plugins.dirtrewards.utils.Utilities;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class RewardsManager {
	private static List<Block> blocks;
	private static List<CommandReward> commandRewards;
	private static List<MoneyReward> moneyRewards;

	public static void init() {
		DatabaseOperation.getData((blockList, commands, money) -> {
			blocks = new ArrayList<>();
			blocks.addAll(blockList);
			commandRewards = new ArrayList<>();
			commandRewards.addAll(commands);
			moneyRewards = new ArrayList<>();
			moneyRewards.addAll(money);

			if (Utilities.config.general.debug) {
				Utilities.log(Level.WARNING, "Loaded " + blocks.size() + " rewards.");
			}
		});
	}

	public static void addBlock(Block block) {
		blocks.add(block);
	}

	public static void removeBlock(Block block) {
		blocks.removeIf(b -> b.getNamespacedKey().equals(block.getNamespacedKey()));
		commandRewards.removeIf(commandReward -> commandReward.getBlockNameSpacedKey().equals(block.getNamespacedKey()));
		moneyRewards.removeIf(moneyReward -> moneyReward.getBlockNameSpacedKey().equals(block.getNamespacedKey()));
	}

	public static boolean doesBlockExist(Block block) {
		for (Block b : blocks) {
			if (b.getNamespacedKey().equals(block.getNamespacedKey())) {
				return true;
			}
		}

		return false;
	}

	public static List<Block> getBlocks() {
		return blocks;
	}

	public static MoneyReward getMoneyReward(Block block) {
		MoneyReward reward = null;
		for (MoneyReward moneyReward : moneyRewards) {
			if (moneyReward.getBlockNameSpacedKey().equals(block.getNamespacedKey())) {
				reward = moneyReward;
			}
		}
		return reward;
	}

	public static List<CommandReward> getCommandRewards(Block block) {
		List<CommandReward> commands = new ArrayList<>();
		for (CommandReward commandReward : commandRewards) {
			if (commandReward.getBlockNameSpacedKey().equals(block.getNamespacedKey())) {
				commands.add(commandReward);
			}
		}
		return commands;
	}

	public static boolean doesCommandRewardExist(CommandReward commandReward) {
		for (CommandReward command : commandRewards) {
			if (command.getBlockNameSpacedKey().equals(commandReward.getBlockNameSpacedKey()) && command.getCommand().equals(commandReward.getCommand())) {
				return true;
			}
		}

		return false;
	}

	public static boolean doesMoneyRewardExist(NamespacedKey namespacedKey) {
		for (MoneyReward money : moneyRewards) {
			if (money.getBlockNameSpacedKey().equals(namespacedKey)) {
				return true;
			}
		}

		return false;
	}

	public static void addCommandReward(CommandReward commandReward) {
		commandRewards.add(commandReward);
	}

	public static void removeCommandReward(CommandReward commandReward) {
		commandRewards.remove(commandReward);
	}

	public static void addMoneyReward(MoneyReward moneyReward) {
		moneyRewards.add(moneyReward);
	}

	public static void removeMoneyReward(NamespacedKey namespacedKey) {
		for (MoneyReward money : moneyRewards) {
			if (money.getBlockNameSpacedKey().equals(namespacedKey)) {
				moneyRewards.remove(money);
				return;
			}
		}
	}
}
